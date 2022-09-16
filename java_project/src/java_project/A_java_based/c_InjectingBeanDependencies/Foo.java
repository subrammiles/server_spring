package java_project.A_java_based.c_InjectingBeanDependencies;

public class Foo {
    Bar bar;
    public Foo(Bar bar){
        this.bar=bar;
    }
    public void addTwoNumbers(int a,int b){
        int sum=bar.addTwoNumbers(a,b);
        System.out.println("Sum is "+sum);
    }
}
