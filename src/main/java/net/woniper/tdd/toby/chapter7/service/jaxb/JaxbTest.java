package net.woniper.tdd.toby.chapter7.service.jaxb;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
    }
}
