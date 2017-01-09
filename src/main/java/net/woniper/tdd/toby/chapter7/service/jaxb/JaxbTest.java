package net.woniper.tdd.toby.chapter7.service.jaxb;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by woniper on 2017. 1. 9..
 */
public class JaxbTest {

    @Test
    public void readSqlmap() throws JAXBException, IOException {
        String contextPath = Sqlmap.class.getPackage().getName();
        JAXBContext context = JAXBContext.newInstance(contextPath);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        InputStream resourceAsStream = getClass().getResourceAsStream("sqlmap.xml");
        Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(resourceAsStream);

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
