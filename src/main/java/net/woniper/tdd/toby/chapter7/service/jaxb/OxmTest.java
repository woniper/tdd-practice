package net.woniper.tdd.toby.chapter7.service.jaxb;

import net.woniper.tdd.toby.chapter7.DaoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by woniper on 2017. 1. 9..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class OxmTest {

    @Autowired private Unmarshaller unmarshaller;

    @Test
    public void unmarshallSqlMap() throws Exception {
        Source xmlSource = new StreamSource(getClass().getResourceAsStream("sqlmap.xml"));

        Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(xmlSource);

        List<SqlType> sqlList = sqlmap.getSql();
        assertThat(sqlList.size(), is(3));
        assertThat(sqlList.get(0).getKey(), is("add"));
        assertThat(sqlList.get(1).getKey(), is("get"));
        assertThat(sqlList.get(2).getKey(), is("delete"));

        assertThat(sqlList.get(0).getValue(), is("insert"));
        assertThat(sqlList.get(1).getValue(), is("select"));
        assertThat(sqlList.get(2).getValue(), is("delete"));

    }
}
