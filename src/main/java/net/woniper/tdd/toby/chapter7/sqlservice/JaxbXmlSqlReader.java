package net.woniper.tdd.toby.chapter7.sqlservice;

import net.woniper.tdd.toby.chapter6.dao.UserDao;
import net.woniper.tdd.toby.chapter7.service.jaxb.SqlType;
import net.woniper.tdd.toby.chapter7.service.jaxb.Sqlmap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * Created by woniper on 2017. 1. 9..
 */
public class JaxbXmlSqlReader implements SqlReader {

    private static final String DEFAULT_SQLMAP_FILE = "sqlmap.xml";
    private String sqlmapFile = DEFAULT_SQLMAP_FILE;

    public JaxbXmlSqlReader setSqlmapFile(String sqlmapFile) {
        this.sqlmapFile = sqlmapFile;
        return this;
    }

    @Override
    public void read(SqlRegistry sqlRegistry) {
        String contextPath = Sqlmap.class.getPackage().getName();

        try {
            JAXBContext context = JAXBContext.newInstance(contextPath);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStream is = UserDao.class.getClassLoader().getResourceAsStream(this.sqlmapFile);
            Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(is);

            for (SqlType sql : sqlmap.getSql()) {
                sqlRegistry.registerSql(sql.getKey(), sql.getValue());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
