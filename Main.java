import java.io.*;
import java.util.Scanner;

class Calculator {
    private double x;
    private double y;

    public void calculate(double x) {
        this.x = x;
        this.y = x - Math.sin(x);
    }

    public void saveToFile(String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(String.format("%.4f ", x));
            fw.write(String.format("%.4f ", y));
            System.out.println("Cохранено в файл: " + filename);
        } catch (IOException ex) {
            System.out.println("Ошибка при сохранении: " + ex.getMessage());
        }
    }

    public void loadFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String xTwo = br.readLine();
            String yTwo = br.readLine();
            if (xTwo != null && yTwo != null) {
                this.x = Double.parseDouble(xTwo);
                this.y = Double.parseDouble(yTwo);
                System.out.println("Загружено из файла: " + filename);
            } else {
                System.out.println("Файл (" + filename + ") поврежден или пуст.");
            }
        } catch (IOException ex) {
            System.out.println("Ошибка при загрузке: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Неверный формат данных в файле (" + filename + ").");
        }
    }

    public void displayState() {
        System.out.printf("x = %.4f, y = %.4f \n", x, y);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();
        String fileName = "text.txt";

        while (true) {
            System.out.println("Введите число x (save, upload или exit):");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            } else if (input.equalsIgnoreCase("save")) {
                calculator.saveToFile(fileName);
            } else if (input.equalsIgnoreCase("upload")) {
                calculator.loadFile(fileName);
                calculator.displayState();
            } else {
                try {
                    double value = Double.parseDouble(input);
                    calculator.calculate(value);
                    calculator.displayState();
                } catch (NumberFormatException ex) {
                    System.out.println("Попробуйте снова. Команды введены не правильно");
                }
            }
        }
    }
}