package ru.test.tasks;

public class Init {
    public static class Base {
        int i;
        void init() {
            i++;
        }
        public void print() {
            System.out.println(i);
        }
        public Base() {
            init();
        }
    }
    public static class Derived extends Base {
        int j = 2;
        void init() {
            i += j;
            j++;
        }
        public void print() {
            super.print();
            System.out.println(j);
        }
    }
}
