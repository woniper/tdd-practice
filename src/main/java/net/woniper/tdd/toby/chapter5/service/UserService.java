package net.woniper.tdd.toby.chapter5.service;

import net.woniper.tdd.toby.chapter5.Level;
import net.woniper.tdd.toby.chapter5.User;
import net.woniper.tdd.toby.chapter5.dao.UserDao;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * Created by woniper on 2016. 12. 31..
 */
public class UserService {

    private UserDao userDao;
    private PlatformTransactionManager transactionManager;
    private MailSender mailSender;

    public static final int MIN_LOGCOUNT_FOR_SILBER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void upgradeLevels() {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            List<User> users = userDao.getAll();

            for (User user : users) {
                if(canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            this.transactionManager.commit(status);
        } catch (RuntimeException e) {
            this.transactionManager.rollback(status);
            throw e;
        }
    }

    public void add(User user) {
        if(user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }

        userDao.add(user);
    }

    /**
     * upgrade 여부도 User에 존재하면 안되나?
     * @param user
     * @return
     */
    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();

        switch (currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILBER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level:" + currentLevel);
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("admin@woniper.net");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " + user.getLevel());

        this.mailSender.send(mailMessage);
    }
}
