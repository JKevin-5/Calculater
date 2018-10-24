package com.example.user.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.math.BigDecimal;
import java.util.Stack;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String history_text ="";//历史文本
    EditText edit;   //当前运算
    EditText history; //计算历史
    boolean isOp_Down = false;//运算符是否按过，默认为flase
    boolean isPo_Down = false;//"."是否按过，默认为flase
    boolean isEq_Down = false;//等号是否按过，默认为flase



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText)findViewById(R.id.edit);
        history = (EditText)findViewById(R.id.history);
        Button button_0 = (Button)findViewById(R.id.earo);
        Button button_1 = (Button)findViewById(R.id.one);
        Button button_2 = (Button)findViewById(R.id.two);
        Button button_3 = (Button)findViewById(R.id.three);
        Button button_4 = (Button)findViewById(R.id.four);
        Button button_5 = (Button)findViewById(R.id.five);
        Button button_6 = (Button)findViewById(R.id.six);
        Button button_7 = (Button)findViewById(R.id.seven);
        Button button_8 = (Button)findViewById(R.id.eight);
        Button button_9 = (Button)findViewById(R.id.nine);
        Button button_ac = (Button)findViewById(R.id.allclear);
        Button button_del = (Button)findViewById(R.id.delect);
        Button button_div = (Button)findViewById(R.id.division);
        Button button_mult = (Button)findViewById(R.id.multiplication);
        Button button_sub = (Button)findViewById(R.id.subtraction);
        Button button_plus =(Button)findViewById(R.id.plus);
        Button button_point=(Button)findViewById(R.id.point);
        Button button_equal=(Button)findViewById(R.id.equal);


        button_0.setOnClickListener(this);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
        button_9.setOnClickListener(this);
        button_ac.setOnClickListener(this);
        button_del.setOnClickListener(this);
        button_div.setOnClickListener(this);
        button_equal.setOnClickListener(this);
        button_mult.setOnClickListener(this);
        button_plus.setOnClickListener(this);
        button_point.setOnClickListener(this);
        button_sub.setOnClickListener(this);

        edit.setMovementMethod(ScrollingMovementMethod.getInstance());//使得文本框可以实现滚动

    }

    @Override
    //重写onCreateOptionsMenu方法给活动创建菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.science:
                /* 新建一个Intent对象 */
                Intent intent1 = new Intent(MainActivity.this,ScienceActivity.class);
                /* 指定intent要启动的类 */
               // intent.setClass(Activity01.this, Activity02.class);
                /* 启动一个新的Activity */
                startActivity(intent1);
                /* 关闭当前的Activity */
                MainActivity.this.finish();
                break;
            default:
                break;
        }
        return true;
    }

    //按钮的点击事件
    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.earo: num("0");break;
            case R.id.one: num("1");break;
            case R.id.two: num("2");break;
            case R.id.three: num("3");break;
            case R.id.four: num("4");break;
            case R.id.five: num("5");break;
            case R.id.six: num("6");break;
            case R.id.seven: num("7");break;
            case R.id.eight: num("8");break;
            case R.id.nine: num("9");break;
            case R.id.plus: op("+");break;
            case R.id.subtraction: op("-");break;
            case R.id.division: op("÷");break;
            case R.id.multiplication: op("×");break;

            case R.id.allclear:
                isOp_Down = false;
                isPo_Down = false;
                edit.setText("0");
                history.setText("");//清空历史记录
                break;

            case R.id.delect: //判断是否为空，然后在进行删除
                String str_text = edit.getText().toString();
                int length = str_text.length();
                if(Pattern.matches("^=[0-9].*",str_text)){
                    edit.setText("0");
                }else{
                    if(length>0){
                        String word = str_text.substring(length - 1,length);
                        if(word.equals("."))
                            isPo_Down = false;
                        if(word.equals("+")|| word.equals("-")||word.equals("x")||word.equals("÷"))
                            isOp_Down = false;
                        edit.setText(str_text.substring(0,length - 1));
                    }
                }break;

            case R.id.point:
                String str_t = edit.getText().toString();
                if (!isPo_Down){
                    isPo_Down = true;
                    if (Pattern.matches("^=[0-9].*",str_t))
                        str_t="0";
                    edit.setText(str_t+".");
                }
                break;

            case R.id.equal:
                equal();
                break;
            default: break;
        }
        edit.setSelection(edit.getText().length());//使文本光标聚焦到输入位置
        history.setSelection(history.getText().length());
    }
    //数字按钮点击事件
    private void num(String num){
        String str_text = edit.getText().toString();
        isOp_Down = false;

        if(str_text.equals("0")|| Pattern.matches("^=[0-9].*",str_text)){//若文本为0或者文本为运算结果
            edit.setText(num);
        }else {
            edit.setText(str_text+num);
        }
    }
    //运算符按钮点击事件
    private void op(String op){
        if (!isOp_Down){//运算符被按下
            String str_text = edit.getText().toString();
            isOp_Down=true;
            isPo_Down=false;//刷新小数点的点击事件
            if (Pattern.matches("^=[0-9].*",str_text))//若文本为运算结果则利用运算结果进行计算
                str_text = str_text.substring(1,str_text.length());
            edit.setText(str_text + op);
        }
    }

    private void equal(){
        try{
        String str_text = edit.getText().toString();
        isEq_Down = true;
        int length = str_text.length();
        Pattern pattern = Pattern.compile("[0-9]*");

        if(Pattern.matches("^=[0-9].*", str_text)){//若以计算结果结尾
            edit.setText(str_text);
        }else {
            if(Pattern.matches(".*[\\+\\-\\×\\÷\\.]$", str_text)) {//若以运算符结尾则忽略此运算符
                str_text = str_text.substring(0, length - 1);
            }
            String postfixExp = getPostfixExp(str_text);//将中缀表达式转换为后缀表达式
            edit.setText("="+calPostfix(postfixExp));//计算后缀表达式
            history_text=history_text+"\n"+str_text+"="+calPostfix(postfixExp)+"\n——————————————";
            history.setText(history_text);//保存历史运算表达式
            }}catch (Exception e){
            edit.setText("运算出错！");
        }
    }

    //将中缀表达式转换为后缀表达式
    private String getPostfixExp(String str) {
        String postfix = "";
        String numString = "";
        Stack numStack = new Stack();//数字栈
        Stack opStack = new Stack();//字符栈

        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(Character.isDigit(ch) || ch == '.') {    //判定ch 是否是数字 或者是 .
                numString += String.valueOf(ch);        //将数字和 .放入numString，等待下一个运算符
            } else {                                    //ch为运算符时
                if(numString.length() > 0) {
                    numStack.push(numString);           //将此运算符前数字压入数字栈
                    numString = "";                     //压入栈后，初始化 numString
                }
                opPush(opStack, numStack, ch);
            }
        }

        //最后判定numString是否为空，因为最后一个可能是数字，没有运算符进行判定
        if(numString.length() > 0)
            numStack.push(numString);

        //检测完后，将运算符栈中转入到数字栈中
        while(!opStack.empty()) {
            numStack.push(opStack.pop());
        }
        //将数字栈打印出来得到后缀表达式
        //此处需要将字符串逆序，才得到后缀表达式，但是有小数点的存在，不能直接用 reverse 的逆序函数
        //通过两个栈的先进后出特点，得到栈的逆序
        while(!numStack.empty()) {
            opStack.push(numStack.pop());
        }
        while(!opStack.empty()) {
            postfix = postfix + String.valueOf(opStack.pop()) + " ";
        }
        return postfix;
    }

    //计算后缀表达式
    private String calPostfix(String str) {
        String result = "";
        Stack numStack = new Stack();
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(ch == ' ') {
                //存在运算符时
                if(result.length() > 0 && (result.equals("+") || result.equals("-") || result.equals("×") || result.equals("÷")))
                {
                    double num = 0;
                    double secondNum = Double.parseDouble(String.valueOf(numStack.pop()));
                    double firstNum = Double.parseDouble(String.valueOf(numStack.pop()));
                    switch (result) {
                        case "+":
                            num = firstNum + secondNum;break;
                        case "-":
                            num = firstNum - secondNum;break;
                        case "×":
                            num = firstNum * secondNum;break;
                        case "÷":
                            num = firstNum / secondNum;break;
                    }
                    numStack.push(num);
                }
                else if(result.length() > 0) {
                    numStack.push(result);
                }
                result = "";
            } else {
                result += String.valueOf(ch);
            }
        }
        return BigDecimal.valueOf(Double.valueOf(String.valueOf(numStack.pop()))).stripTrailingZeros().toPlainString();
    }
    //获取运算符权重
    private int getOpWeight(char ch) {
        // + - 权重为1
        if(ch == '+' || ch == '-') return 1;
        //× ÷ 权重为2
        if(ch == '×' || ch == '÷') return 2;
        //(） 权重为3
        //if(ch == '('||ch == ')')return 3;
        return -1;
    }

    //将运算符压入栈
    private void opPush(Stack opStack, Stack numStack, char ch) {
        if(canOpPush(opStack, ch)) {    //判定能否将运算符压入栈内
            opStack.push(ch);           //true则压入栈内
        } else {                        //false（即 待压入运算符优先级 <= 栈顶运算符优先级）
            //将栈顶运算符取出压入数字栈
            numStack.push(String.valueOf(opStack.pop()));
            //此处需要递归判定，弹出所有优先级 >= 该运算符的栈顶元素
            opPush(opStack, numStack, ch);
        }
    }

    //判定运算符能否压入运算符栈
    private Boolean canOpPush(Stack opStack, char ch) {
        //当运算符栈为空时，返回true；或当待压入运算符权重大于栈顶权重，返回true
        if(opStack.empty() || (getOpWeight(ch) > getOpWeight(String.valueOf(opStack.peek()).charAt(0))))
            return true;
        //其他情况返回false
        return false;
    }


}
