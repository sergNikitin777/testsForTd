package ru.test.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {



        System.out.println("Creating Executor Service with a thread pool of Size 2");
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable task1 = () -> {
            System.out.println("Executing Task1 inside : " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        };

        Runnable task2 = () -> {
            System.out.println("Executing Task2 inside : " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        };

        Runnable task3 = () -> {
            System.out.println("Executing Task3 inside : " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        };


        System.out.println("Submitting the tasks for execution...");
        executorService.submit(task1);
        executorService.submit(task2);
        executorService.submit(task3);

        executorService.shutdown();

    }


    /*
    Test Algorithm 4
    Напишите эффективную реализацию функции поиска индекса заданного значения в упорядоченном массиве чисел.
    */
    public static int find(int[] nums, int valueToFind){
        int i = -1;
        if (nums != null) {
            int low = 0, high = nums.length, mid;
            while (low < high) {
                mid = (low + high)/2; // Можно заменить на: (low + high) >>> 1, чтоб не возникло переполнение целого
                if (valueToFind == nums[mid]) {
                    i = mid;
                    break;
                } else {
                    if (valueToFind < nums[mid]) {
                        high = mid;
                    } else {
                        low = mid + 1;
                    }
                }
            }
        }
        return i;
    }

    /*
    Напишите реализацию функции определения високосного года для даты григорианского календаря в формате “DD-MM-YYYY”
    с возможными значениями года в диапазоне от 1600 до 9999.
     */
    public static boolean isLeapYear(String date) throws Exception {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
        final Date parseDate = dateFormat.parse(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate);

        final Integer year = cal.get(Calendar.YEAR)+1;

        if (year>=1600&&year<=9999) {
            return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
        }else{
            throw new Exception("год вне допустимого диапазона");
        }
    }

}
