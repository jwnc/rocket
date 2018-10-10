package demo.proxy.jdk;

public class BookFacadeImpl implements BookFacade {
    @Override
    public void addBook() {
        System.out.println("BookFacadeImpl增加图书方法。。。");
    }

    @Override
    public final void finalMethod() {
        System.out.println("finalMethod...");
    }
}