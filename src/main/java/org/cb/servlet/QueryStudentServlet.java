package org.cb.servlet;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.cb.entity.Student;
import org.cb.mapper.StudentMapper;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.List;

@WebServlet("/QueryStudentServlet")
public class QueryStudentServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader,"development");
        SqlSession session = sessionFactory.openSession();
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        List<Student> students = studentMapper.queryAllStudents();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("students",students);
        OutputStream out = response.getOutputStream();
        out.write(jsonObject.toString().getBytes("UTF-8"));
        out.close();
        session.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }
}
