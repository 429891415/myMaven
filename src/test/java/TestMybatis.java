import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.cb.entity.Student;
import org.cb.mapper.StudentMapper;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class TestMybatis {

//    查询单个学生
    public static void queryStudentByStuno() throws IOException {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader,"development");
        SqlSession session = sessionFactory.openSession();
//        String statement = "org.cb.mapper.StudentMapper.queryStudentByStuno";
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        Student student = studentMapper.queryStudentByStuno(1);
        System.out.println(student);
        session.close();
    }
//    查询全部学生
    public static void queryAllStudent() throws IOException {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader,"development");
        SqlSession session = sessionFactory.openSession();
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        List<Student> students = studentMapper.queryAllStudents();
        System.out.println(students);
        session.close();
    }
//    增加学生
    public static void addStudent() throws IOException {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader,"development");
        SqlSession session = sessionFactory.openSession();
        Student student = new Student(3,"wt",20,"g2");
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        studentMapper.addStudent(student);
        session.commit();//JDBC要手动提交*****
        System.out.println("增加成功");
        session.close();
    }
//    修改学生
    public static void updateStudentByStuno() throws IOException {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader,"development");
        SqlSession session = sessionFactory.openSession();
        String statement = "org.cb.mapper.StudentMapper.updateStudentByStuno";
        Student student = new Student(3,"tt",20,"g2");
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        studentMapper.updateStudentByStuno(student);
        session.commit();//JDBC要手动提交*****
        System.out.println("修改成功");
        session.close();
    }
//    删除学生
    public static void deleteStudentByStuno() throws IOException {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader,"development");
        SqlSession session = sessionFactory.openSession();
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        studentMapper.deleteStudentByStuno(3);
        session.commit();//JDBC要手动提交*****
        System.out.println("删除成功");
        session.close();
    }
    public static void main(String[] args) throws IOException {
//        queryStudentByStuno();
        queryAllStudent();
//        addStudent();
//        updateStudentByStuno();
//        deleteStudentByStuno();
//        queryAllStudent();

    }
}
