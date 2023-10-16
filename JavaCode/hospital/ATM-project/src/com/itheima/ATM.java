package com.itheima;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account>accounts = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private Account loginAcc;//记住登录后的用户账户

    public void start(){
        while (true) {
            System.out.println("=====欢迎进入ATM系统=====");
            System.out.println("1、用户登录");
            System.out.println("2、用户开户");
            System.out.println("请选择:");
            int command = sc.nextInt();
            switch(command){
                case 1:
                    //用户登录
                    login();
                    break;
                case 2:
                    creatAccount();
                    //用户开户
                    break;
                default:
                    System.out.println("没有该操作！");
            }
        }
    }


    private void login(){  //完成用户的登录操作
        System.out.println("=====系统登录=====");
        if(accounts.size() == 0){
            System.out.println("当前系统没有账户，请先注册！");
            return;
        }

        while (true) {
            System.out.println("请输入您的登录卡号：");
            String cardId = sc.next();
            Account acc = getAccountByCardId(cardId);
            if(acc == null){
                System.out.println("该账户不存在，请检查输入或注册账户！");
            }else{
                while (true) {
                    System.out.println("请输入登录密码：");
                    String psw = sc.next();
                    if(psw.equals(acc.getPassword())){
                        System.out.println("恭喜您，"+ acc.getUserName() + "登录成功，您的卡号是：" + acc.getCardId());
                        loginAcc = acc;
                        showUserCommand();
                        return;//跳出并结束当前登录方法，回到欢迎页
                    }else{
                        System.out.println("密码错误，请重新输入！");
                    }
                }

            }
        }

    }

    //展示登录后的操作界面
    private void showUserCommand(){
        while (true) {
            System.out.println(loginAcc.getUserName() + "您可以选择如下功能进行账户的处理：");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.密码修改");
            System.out.println("6.退出登录");
            System.out.println("7.注销当前账户");
            int command = sc.nextInt();
            switch (command){
                case 1:
                    //1.查询账户
                    showLoginAccount();
                    break;
                case 2:
                    //2.存款
                    break;
                case 3:
                    //3.取款
                    break;
                case 4:
                    //4.转账
                    break;
                case 5:
                    //5.密码修改
                    break;
                case 6:
                    //6.退出登录
                    return;
                case 7:
                    //7.注销当前账户
                    break;
                default:
                    System.out.println("您输入的命令有误，请重新输入！");
            }
        }
    }
    //展示当前登陆的账户信息
    private void showLoginAccount(){
        System.out.println("======当前您的账户信息如下======");
        System.out.println("卡号:" + loginAcc.getCardId());
        System.out.println("户主:" + loginAcc.getUserName());
        System.out.println("性别：" + loginAcc.getSex());
        System.out.println("余额：" + loginAcc.getMoney());
        System.out.println("每次取现额度：" + loginAcc.getLimit());
    }

    //创建账户
    private void creatAccount(){
        //1.创建一个账户对象，用于封装用户的开户信息
        Account acc = new Account();
        //2.需要用户输入自己的开户信息，赋值给账户对象
        System.out.println("请输入您的账户名称：");
        String userName = sc.next();
        acc.setUserName(userName);

        while (true) {
            System.out.println("请输入您的性别：");
            char sex = sc.next().charAt(0);
            if(sex == '男' || sex == '女'){
                acc.setSex(sex);
                break;
            }else{
                System.out.println("您输入的性别有误，只能是男或者女，请重新输入！");
            }
        }

        while (true) {
            System.out.println("请输入您的密码：");
            String psw1 = sc.next();
            System.out.println("请确认您的密码：");
            String psw2 = sc.next();
            if(psw1.equals(psw2)){
                acc.setPassword(psw1);
                break;
            }
            System.out.println("两次密码不一致，请重新输入！");
        }

        System.out.println("请输入您的取款限额：");
        double limit = sc.nextDouble();
        acc.setLimit(limit);

        //生成8位卡号
        String cardId = createCardId();
        acc.setCardId(cardId);

        //3.把这个账户对象存入到账户集合中去
        accounts.add(acc);
        System.out.println("恭喜您，"+ acc.getUserName() + "开户成功，您的卡号是：" + acc.getCardId());
    }

    //生成卡号
    private String createCardId(){
        while (true) {
            String cardId = "";
            Random r = new Random();
            for(int i = 0;i < 8;i++){
                int data = r.nextInt(10);//0-9
                cardId += data;
            }
            Account acc = getAccountByCardId(cardId); //判断卡号是否重复
            if(acc == null){
                return cardId;
            }
        }
    }

    //根据卡号查询账户对象返回
    private Account getAccountByCardId(String cardId){
        //遍历全部的账户对象
        for(int i = 0;i < accounts.size();i++){
            Account acc = accounts.get(i);
            if(acc.getCardId().equals(cardId)){
                return acc;
            }
        }
        return null;//查无此账户，这个卡号不存在
    }
}
