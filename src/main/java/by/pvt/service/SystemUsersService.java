package by.pvt.service;

import by.pvt.basic.SystemUsers;
import by.pvt.mapper.SystemUsersMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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

    protected void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<SystemUsers> getSystemUsers() {
        List<SystemUsers> users = null;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        if (sqlSession != null) {
            SystemUsersMapper dao = sqlSession.getMapper(SystemUsersMapper.class);
            users = dao.selectByExample(null);
            sqlSession.close();
        } else {
            log.info("sqlSession is null");
        }
        return users;
    }


    public void add(SystemUsers systemUser) {
        if (systemUser == null) {
            log.info("The input of systemUser is null");
            return;
        }
        SqlSession sqlSession = sqlSessionFactory.openSession();
        if (sqlSession == null) {
            log.info("sqlSession is null");
            return;
        }
        SystemUsersMapper dao = sqlSession.getMapper(SystemUsersMapper.class);
        try {
            int result = dao.insert(systemUser);
            log.info("Added new system user: " + result);
            sqlSession.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    public void addAll(List<SystemUsers> systemUsers) {
        if (systemUsers == null) {
            log.info("The input of systemUsers is null");
            return;
        }
        SqlSession sqlSession = sqlSessionFactory.openSession();
        if (sqlSession == null) {
            log.info("Session is null");
            return;
        }
        SystemUsersMapper dao = sqlSession.getMapper(SystemUsersMapper.class);
        try {
            systemUsers.stream()
                    .filter(Objects::nonNull)
                    .forEach(dao::insert);
            sqlSession.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    public void updateByPrimaryKey(SystemUsers systemUser) {
        if (systemUser == null) {
            log.info("The input of systemUser is null");
            return;
        }
        SqlSession sqlSession = sqlSessionFactory.openSession();
        if (sqlSession == null) {
            log.info("sqlSession is null");
            return;
        }
        SystemUsersMapper dao = sqlSession.getMapper(SystemUsersMapper.class);
        try {
            dao.updateByPrimaryKey(systemUser);
            log.info("User updated");
            sqlSession.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    public void deleteByPrimaryKey(Integer id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        if (sqlSession == null) {
            log.info("sqlSession is null");
            return;
        }
        SystemUsersMapper dao = sqlSession.getMapper(SystemUsersMapper.class);
        try {
            dao.deleteByPrimaryKey(id);
            sqlSession.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            sqlSession.close();
        }
    }
}
