package com.example.oldmansupport.maninfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
/**
 * Created by littlecurl 2018/6/24
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    /**
     * 声明一个AndroidSDK自带的数据库变量db
     */
    private SQLiteDatabase db;

    /**
     * 写一个这个类的构造函数，参数为上下文context，所谓上下文就是这个类所在包的路径
     * 指明上下文，数据库名，工厂默认空值，版本号默认从1开始
     * super(context,"db_test",null,1);
     * 把数据库设置成可写入状态，除非内存已满，那时候会自动设置为只读模式
     * 不过，以现如今的内存容量，估计一辈子也见不到几次内存占满的状态
     * db = getReadableDatabase();
     */

    public DBOpenHelper(Context context){
        super(context,"oldman",null,1);
        db = getReadableDatabase();
    }

    /**
     * 重写两个必须要重写的方法，因为class DBOpenHelper extends SQLiteOpenHelper
     * 而这两个方法是 abstract 类 SQLiteOpenHelper 中声明的 abstract 方法
     * 所以必须在子类 DBOpenHelper 中重写 abstract 方法
     * 想想也是，为啥规定这么死必须重写？
     * 因为，一个数据库表，首先是要被创建的，然后免不了是要进行增删改操作的
     * 所以就有onCreate()、onUpgrade()两个方法
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS oldman(" +
                "_id integer PRIMARY KEY AUTOINCREMENT," +
                "phonenumber varchar(20),"+
                "password varchar(20),"+
                "name varchar(20)," +
                "sex varchar(2)," +
                "birth varchar(20),"+
                "healthstate varchar(20),"+
                "address varchar(50)," +
                "idcard text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
    /**
     * 接下来写自定义的增删改查方法
     * 这些方法，写在这里归写在这里，以后不一定都用
     * add()
     * delete()
     * update()
     * getAllData()
     */
    public void add(String phonenumber,String password,String name,String sex,String birth,String healthstate,String address,String idcard){
        db.execSQL("INSERT INTO oldman (phonenumber,password,name,sex,birth,healthstate,address,idcard) VALUES(?,?,?,?,?,?,?,?)",new Object[]{phonenumber,password,name,sex,birth,healthstate,address,idcard});
    }
    public void delete(String phonenumber,String password){
        db.execSQL("DELETE FROM oldman WHERE phonenumber = AND password ="+phonenumber+password);
    }
    public void updatepsd(String password){
        db.execSQL("UPDATE oldman SET password = ?",new Object[]{password});
    }

    public void updateinfo(String name,String sex,String birth,String healthstate,String address,int _id){
        db.execSQL("UPDATE oldman SET name=?,sex=?,birth=?,healthstate=?,address=? WHERE _id=?",new Object[]{name,sex,birth,healthstate,address,_id});

    }

    /**
     * 前三个没啥说的，都是一套的看懂一个其他的都能懂了
     * 下面重点说一下查询表user全部内容的方法
     * 我们查询出来的内容，需要有个容器存放，以供使用，
     * 所以定义了一个ArrayList类的list
     * 有了容器，接下来就该从表中查询数据了，
     * 这里使用游标Cursor，这就是数据库的功底了，
     * 在Android中我就不细说了，因为我数据库功底也不是很厚，
     * 但我知道，如果需要用Cursor的话，第一个参数："表名"，中间5个：null，
     *                                                     最后是查询出来内容的排序方式："name DESC"
     * 游标定义好了，接下来写一个while循环，让游标从表头游到表尾
     * 在游的过程中把游出来的数据存放到list容器中
     * @return
     */
    public ArrayList<User> getAllData(){

        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("oldman",null,null,null,null,null,"name DESC");
        while(cursor.moveToNext()){
            int _id=cursor.getInt(cursor.getColumnIndex("_id"));
            String phonenumber = cursor.getString(cursor.getColumnIndex("phonenumber"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String birth = cursor.getString(cursor.getColumnIndex("birth"));
            String healthstate = cursor.getString(cursor.getColumnIndex("healthstate"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String idcard = cursor.getString(cursor.getColumnIndex("idcard"));
            list.add(new User(_id,phonenumber,password,name,sex,birth,healthstate,address,idcard));
        }
        return list;
    }
}
