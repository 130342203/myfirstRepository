/*
package com.com.config.velocity;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.util.Properties;

*/
/**
 * @author ron
 *         2016/10/9.
 *//*

@Configuration
public class VelocityConfig {

    @Bean
    public VelocityEngine getEngine(){
        VelocityEngine engine = null;
        VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
        Properties properties = new Properties();
        properties.put("input.encoding","utf-8");
        properties.put("output.encoding", "utf-8");
        properties.put("resource.loader", "class");
        properties.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
 //       properties.put("resource.loader", "file,class");
//        properties.put("file.resource.loader.path", "c:\\tmp\\");
//        properties.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        try {
            bean.setVelocityProperties(properties);
            engine = bean.createVelocityEngine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return engine;
    }



//
//    public List<Boolean> booleanList= new ArrayList<Boolean>();
//    public Map map = new HashMap();
//    public int index = 0;
//    static class TemplatedParserContext implements ParserContext {
//        public String getExpressionPrefix(){
//            return "${";
//        }
//        public String getExpressionSuffix(){
//            return "}";
//        }
//        public boolean isTemplate(){
//            return true;
//        }
//    }
//    public static void main(String[] args) {
//        ExpressionParser parser= new SpelExpressionParser();
////        Expression exp=parser.parseExpression("'HelloWorld'.bytes");
////        Object message=exp.getValue();
////
////        System.out.println(message);
//        String a = "{\"code\":200,\"page\":1,\"total\":10,\"msg\":\"ERRMSG.COMMON.OPT_SUCESS\",\"msgData\":[],\"data\":[{\"CODE\":\"test9\",\"DESCRIPTION\":\"1\",\"PREC\":1,\"LENGTH\":1,\"TYPE\":1,\"DATA_TYPE\":\"NVARCHAR2\",\"ROW_ID\":1},{\"CODE\":\"H001\",\"DESCRIPTION\":\"货号\",\"PREC\":0,\"LENGTH\":30,\"TYPE\":1,\"DATA_TYPE\":\"NVARCHAR2\",\"ROW_ID\":2},{\"CODE\":\"Y001\",\"DESCRIPTION\":\"权限类型\",\"PREC\":0,\"LENGTH\":1,\"TYPE\":1,\"DATA_TYPE\":\"NVARCHAR2\",\"ROW_ID\":3},{\"CODE\":\"d1\",\"DESCRIPTION\":\"test01\",\"VALUE\":\"1,2\",\"TYPE\":0,\"ROW_ID\":4},{\"CODE\":\"d2\",\"DESCRIPTION\":\"test02\",\"VALUE\":\"2,5\",\"TYPE\":0,\"ROW_ID\":5},{\"CODE\":\"d3\",\"DESCRIPTION\":\"test03\",\"VALUE\":\"5,6,7,8\",\"TYPE\":0,\"ROW_ID\":6},{\"CODE\":\"code1\",\"DESCRIPTION\":\"123\",\"PREC\":0,\"LENGTH\":10,\"TYPE\":2,\"DATA_TYPE\":\"NVARCHAR2\",\"ROW_ID\":7},{\"CODE\":\"JYORN\",\"DESCRIPTION\":\"是否标识\",\"PREC\":0,\"LENGTH\":1,\"TYPE\":1,\"DATA_TYPE\":\"NVARCHAR2\",\"ROW_ID\":8},{\"CODE\":\"test45\",\"DESCRIPTION\":\"1\",\"PREC\":1,\"LENGTH\":1,\"TYPE\":1,\"DATA_TYPE\":\"NVARCHAR2\",\"ROW_ID\":9},{\"CODE\":\"test2\",\"DESCRIPTION\":\"1\",\"PREC\":1,\"LENGTH\":1,\"TYPE\":1,\"DATA_TYPE\":\"NVARCHAR2\",\"ROW_ID\":10}]}";
//        String b = "{\"code\":200,\"page\":1,\"total\":9,\"msg\":\"ERRMSG.COMMON.OPT_SUCESS\",\"msgData\":[],\"data\":[{\"FIELD_TYPE\":\"NVARCHAR2\",\"CODE\":\"A001\",\"ELE_NAME\":\"test01\",\"FIELD_LEN\":10,\"DOMAIN_CODE\":\"test2\",\"FIELD_SCALE\":0,\"ROW_ID\":1},{\"FIELD_TYPE\":\"NVARCHAR2\",\"CODE\":\"A002\",\"ELE_NAME\":\"test02\",\"FIELD_LEN\":10,\"DOMAIN_CODE\":\"test3\",\"ROW_ID\":2},{\"FIELD_TYPE\":\"NVARCHAR2\",\"CODE\":\"A003\",\"ELE_NAME\":\"test03\",\"FIELD_LEN\":10,\"DOMAIN_CODE\":\"test\",\"ROW_ID\":3},{\"FIELD_TYPE\":\"NVARCHAR2\",\"CODE\":\"D003\",\"ELE_NAME\":\"日期\",\"FIELD_LEN\":30,\"DOMAIN_CODE\":\"Y001\",\"FIELD_SCALE\":0,\"ROW_ID\":4},{\"FIELD_TYPE\":\"NVARCHAR2\",\"CODE\":\"D001\",\"ELE_NAME\":\"货币\",\"FIELD_LEN\":30,\"DOMAIN_CODE\":\"test3\",\"FIELD_SCALE\":0,\"ROW_ID\":5},{\"FIELD_TYPE\":\"NVARCHAR2\",\"CODE\":\"D002\",\"ELE_NAME\":\"金额\",\"FIELD_LEN\":30,\"DOMAIN_CODE\":\"H001\",\"FIELD_SCALE\":0,\"ROW_ID\":6},{\"FIELD_TYPE\":\"NVARCHAR2\",\"CODE\":\"testtest\",\"ELE_NAME\":\"test\",\"FIELD_LEN\":10,\"DOMAIN_CODE\":\"code1\",\"FIELD_SCALE\":0,\"ROW_ID\":7},{\"FIELD_TYPE\":\"NVARCHAR2\",\"CODE\":\"test\",\"ELE_NAME\":\"test\",\"FIELD_LEN\":10,\"DOMAIN_CODE\":\"test2\",\"FIELD_SCALE\":0,\"ROW_ID\":8},{\"FIELD_TYPE\":\"NVARCHAR2\",\"CODE\":\"D004\",\"ELE_NAME\":\"D0041\",\"FIELD_LEN\":30,\"DOMAIN_CODE\":\"H001\",\"FIELD_SCALE\":0,\"ROW_ID\":9}]}";
//
//        Test simple= new Test();
//        Map tempa = (Map) JSON.parseObject(a, Map.class);
//        Map tempb = (Map)JSON.parseObject(b, Map.class);
//        tempa.putAll(tempb);
//        simple.map = tempa;
//        simple.booleanList.add(true);
//        simple.booleanList.add(false);
//
//        StandardEvaluationContext simpleContext= new StandardEvaluationContext(simple);
//
////        System.out.println(parser.parseExpression("booleanList[${index}]", new TemplatedParserContext()).getValue(simpleContext));
////        simple.index++;
////        System.out.println(parser.parseExpression("booleanList[${index}]", new TemplatedParserContext()).getValue(simpleContext));
//        System.out.println(parser.parseExpression("map['data'][0]['CODE']").getValue(simpleContext));
//        System.out.println(parser.parseExpression("booleanList[index]").getValue(simpleContext));
//
////        System.out.println(parser.parseExpression("booleanList[0]").getValue(simpleContext));
//        simple.index++;
//        System.out.println(parser.parseExpression("booleanList[index]").getValue(simpleContext));
//    }

}
*/
