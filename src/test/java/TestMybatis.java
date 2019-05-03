import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.cb.entity.Person;

import java.io.IOException;
import java.io.Reader;

public class TestMybatis {
    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        session-connection
        SqlSession session = sessionFactory.openSession();
        String statement = "org.cb.mapping.personMapper.queryPersonById";
        Person person = session.selectOne(statement,1);
        System.out.println(person);
        session.close();
    }
}
