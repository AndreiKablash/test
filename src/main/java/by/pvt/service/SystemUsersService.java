package by.pvt.service;

import by.pvt.basic.SystemUsers;
import by.pvt.mapper.SystemUsersMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemUsersService {
    private static Logger log = Logger.getLogger(SystemUsersService.class.getName());
    private SqlSessionFactory sqlSessionFactory;

    public SystemUsersService() {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(
                    Resources.getResourceAsStream("by/pvt/service/mybatis-config.xml"));
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public List<SystemUsers> getSystemUsers() {
        List<SystemUsers> users = null;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            users = sqlSession.getMapper(SystemUsersMapper.class).selectByExample(null);
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return users;
    }

    public void add(SystemUsers systemUser) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            int result = sqlSession.getMapper(SystemUsersMapper.class).insert(systemUser);
            log.info("Added new system user: " + result);
            sqlSession.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void updateByPrimaryKey(SystemUsers systemUser) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.getMapper(SystemUsersMapper.class).updateByPrimaryKey(systemUser);
            log.info("User updated");
            sqlSession.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void deleteByPrimaryKey(Integer id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.getMapper(SystemUsersMapper.class).deleteByPrimaryKey(id);
            sqlSession.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
