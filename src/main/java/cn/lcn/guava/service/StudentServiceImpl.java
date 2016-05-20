package cn.lcn.guava.service;

import cn.lcn.guava.cache.StudentCache;
import cn.lcn.guava.domain.Student;
import cn.lcn.guava.factory.MyCacheFactory;
import com.google.common.cache.Cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class StudentServiceImpl implements StudentService {

    private StudentCache cache;

    private MyCacheFactory<Integer, String> cacheFactory;

    private Cache<Integer, String> cacheSpec;

    @Override
    public String getMsg(int id) {
        // TODO Auto-generated method stub
        String msg = "";
        Cache<Integer, String> studentCache = cache.getsCache();
        try {
            msg = studentCache.get(id, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    // TODO Auto-generated method stub
                    System.out.println("从数据库中取");
                    Student s = new Student();
                    s.setId(1);
                    s.setNameString("liwangcun" + ":time:" + System.currentTimeMillis() / 1000);
                    return s.getNameString();
                }
            });
            return msg;
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

//    @Override
//    public String factory(int id) {
//        // TODO Auto-generated method stub
//        String msg = "";
//        try {
//            Cache<Integer, String> cache = cacheFactory.getObject();
//            msg = cache.get(id, new Callable<String>() {
//                @Override
//                public String call() throws Exception {
//                    // TODO Auto-generated method stub
//                    System.out.println("从数据库中取");
//                    Student s = new Student();
//                    s.setId(1);
//                    s.setNameString("liwangcun" + ":time:" + System.currentTimeMillis() / 1000);
//                    return s.getNameString();
//                }
//            });
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return msg;
//    }

    @Override
    public String spec(int id) {
        // TODO Auto-generated method stub
        String msg = "";
        try {

            msg = cacheSpec.get(id, new Callable<String>() {

                @Override
                public String call() throws Exception {
                    // TODO Auto-generated method stub
                    System.out.println("从数据库中取");
                    Student s = new Student();
                    s.setId(1);
                    s.setNameString("liwangcun" + ":time:" + System.currentTimeMillis() / 1000);
                    return s.getNameString();
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

}
