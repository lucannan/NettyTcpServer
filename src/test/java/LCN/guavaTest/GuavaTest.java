package LCN.guavaTest;

import cn.lcn.guava.domain.Student;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by LCN on 2016/5/20.
 */
public class GuavaTest {

    @Test
    public void test01() {
        Cache<Integer, Student> student = CacheBuilder.newBuilder()
                .expireAfterWrite(8, TimeUnit.SECONDS)
                .initialCapacity(10)
                .maximumSize(12)
                .concurrencyLevel(8)
                .recordStats()
                .removalListener(new RemovalListener<Integer, Student>() {

                    public void onRemoval(
                            RemovalNotification<Integer, Student> arg0) {
                        // TODO Auto-generated method stub
                        System.out.println(arg0.getKey() + " remove because " + arg0.getCause());
                    }
                })
                .build();
        try {
            System.out.println("有的话数据才取出来");
            Student st2 = student.getIfPresent(2);
            System.out.println("getIfPresent方法获取：" + st2);
            //callable必须要有
            Student st = student.get(1, new Callable<Student>() {
                // 如果没有取到key为1的value,那么就返回这个值
                // 这个方法简便地实现了模式"如果有缓存则返回；否则运算、缓存、然后返回"。
                public Student call() throws Exception {
                    // TODO Auto-generated method stub
                    System.out.println("我在get里面");
                    Student student = new Student();
                    student.setId(1);
                    student.setNameString("student");
                    return student;
                }

            });
            TimeUnit.SECONDS.sleep(1);
            System.out.println(st);
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("状态" + student.stats());
    }
}
