package jack28_web;

import cn.syl.utils.MailUtils;

import java.util.ArrayList;

public class Demo {
    public static void main(String ...args) throws Exception {
        //        System.out.println(s1.equals(s2));
        Student s1 = new Student("jack",12);
        Student s2 = new Student("jack",12);
        System.out.println(s1==s2);
        System.out.println(s1.toString());
        System.out.println(s2.toString());

        new Object().hashCode();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    MailUtils.sendMessage("510806100@qq.com","测试邮件2","内容");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}

class Student{
    private int age;
    private String name;



    @Override
    public boolean equals(Object o) {

        //todo:equals 需要覆写hash 吗?

        //覆写hash就为了集合添加, 和判断equals相没关系的
        //不然

        if (this == o) return true;   //感觉 ==就够了
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (age != student.age) return false;
        return name != null ? name.equals(student.name) : student.name == null;
    }

    @Override
    public int hashCode() {
        int result = age;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Student(){}
    public Student( String name,int age) {
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
