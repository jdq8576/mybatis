package cn.edu.njtech.test;

import cn.edu.njtech.dao.UserDao;
import cn.edu.njtech.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author tim
 * @date 2022/9/9 8:25 下午
 */
public class MybatisTest {
    @Test
    public void test() throws IOException {
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

        SqlSessionFactory factory = builder.build(in);

        SqlSession session = factory.openSession();

        UserDao userDao = session.getMapper(UserDao.class);

        List<User> users = userDao.findByName("tim");

        users.forEach(t -> {
            System.out.println(t);
        });

        session.close();
        in.close();
    }
}
