package jetpack.test;

import jetpack.JsonFormatter;
import jetpack.exception.AnnotationNotFoundException;
import jetpack.exception.MissingKeyNameException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class Launcher {
    public static void main(String[] args) {
        Student s1 = new Student("정근철", "대덕소프트웨어마이스터고등학교", "SW개발", 19);
        Student s2 = new Student("서윤호", "대덕소프트웨어마이스터고등학교", "SW개발", 19, s1);
        Student s3 = new Student("박지은", "데일리호텔", "SW개발", 19, s1, s2);

        Integer[] arr = {1, 2, 3, 4};

        int a = 1;
        try {
            System.out.println(JsonFormatter.toJSON(s1));
            System.out.println();
        } catch (AnnotationNotFoundException | MissingKeyNameException e) {
            e.printStackTrace();
        }

        IntPredicate ip;

        filter(students, (Student s) -> s.getAge()== 19);
    }

    public static List<Student> students = new ArrayList<>();

    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T e : list)
            if(p.test(e))
                result.add(e);
        return result;
    }

}
