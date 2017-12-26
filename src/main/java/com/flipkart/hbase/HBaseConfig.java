package com.flipkart.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.sql.ResultSet;

public class HBaseConfig {
    static Configuration config= HBaseConfiguration.create();

    public static Connection getHBaseConfig() throws Exception{
        config.set("hbase.zookeeper.quorum","10.32.73.226");
        config.set("hbase.zookeeper.quorum.property.clientPort","2181");
        Connection connection= ConnectionFactory.createConnection(config);
       return connection;
    }

    public static void main(String[] args) throws Exception {
        Connection connection=getHBaseConfig();
        Table table=connection.getTable(TableName.valueOf("raw_data"));
        Scan scan1 = new Scan();
        ResultScanner scanner1= table.getScanner(scan1);
        for(Result result:scanner1){
            System.out.println(result);
            System.out.println(result.getRow());
//            System.out.println(Bytes.toString(result.getValue("personal".getBytes(),"name".getBytes())));
        }
    }
}
