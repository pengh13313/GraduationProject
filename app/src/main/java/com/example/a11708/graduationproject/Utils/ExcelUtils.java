package com.example.a11708.graduationproject.Utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.example.a11708.graduationproject.Beans.TimeUsingBean;
import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.model.SqlDaoModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.JXLException;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtils {
    private SqlDaoModel mModel;
    private String mUsername;
    private Context context;
    private WritableWorkbook mBook;
    private String s;

    public ExcelUtils(Context context,String Username) {
        this.context = context;
        mUsername = Username;
    }

    public void ExportExcel() {
        try {
            mModel = new SqlDaoModel(context);
            String filePath = "/sdcard/";
            s = filePath + Constant.UserName + ".xls";
            String name = "time";
            List<TimeUsingBean> list = mModel.findAll(mUsername);
            File file = new File(s);//创建excel表
            Cursor c = mModel.Excelcursor();
            SqliteExportExcel(name, file, c);

        }catch (Exception e){
            e.printStackTrace();
        }



    }
    //导出excel表
    private void SqliteExportExcel(String name, File file, Cursor c) throws IOException, JXLException {
        mBook = Workbook.createWorkbook(file);
        WritableSheet sheet = mBook.createSheet(name, 0);//在excel文件中创建表单
        String[] ZiDuan = c.getColumnNames();//获取所有字段的数据集合
        int row = 0;//excel的行数从0开始，为字段头
        for (int i = 0; i < ZiDuan.length; i++) {// 写入字段头
            Label label = new Label(i, row, ZiDuan[i]);
            sheet.addCell(label);
        }
        while (c.moveToNext()) {
            row++;//对应excel中的行数
            for (int i = 0; i < ZiDuan.length; i++) {//i对应到excel中的每一列
                Label label = new Label(i, row, c.getString(i));//创建数据对象
                sheet.addCell(label);//添加写入事件
            }
        }

        mBook.write();//开始执行写入
        mBook.close();//关闭资源
        Toast.makeText(context,"导出完毕",Toast.LENGTH_SHORT).show();
        openFile(s);
    }

    private void openFile(String filePath){


        //File file = new File(filePath);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//版本大于等于7.0
            // 通过FileProvider创建一个content类型的Uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 临时授权
            uri = FileProvider.getUriForFile(context, "com.example.a11708.fileprovider", new File(filePath));

        } else {
            uri = Uri.fromFile(new File(filePath));
        }
        intent.setDataAndType(uri, "*/*");
        context.startActivity(intent);


    }
}
