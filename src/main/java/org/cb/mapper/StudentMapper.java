package org.cb.mapper;

import org.cb.entity.Student;

import java.util.List;

//操作mybatis的接口
public interface StudentMapper {
    /*
    *约定：
    *   1.方法名和mapper.xml的id值相同
    *   2.参数和parameterType一致
    *   3.方法的返回值和resultType一致
    *   4.namespace的值就是接口的全类名（接口-Mapper.xml）
    *   5.一般接口和xml文件放在一个包中
    */
    //public abstract Student queryStudentByStuno(int stuno);   // 默认为public abstract
    Student queryStudentByStuno(int stuno);
    List<Student> queryAllStudents();
    void addStudent(Student student);
    void updateStudentByStuno(Student student);
    void deleteStudentByStuno(int stuno);
}
