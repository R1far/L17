import java.io.*;
import java.util.Scanner;

class Calculator implements Serializable {
    private double x;
    private double y;

    public void calculate(double x) {
        this.x = x;
        this.y = x - Math.sin(x);
    }

    public void saveFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
            System.out.println("Сохранено в файл: " + filename);
        } catch (IOException ex) {
            System.out.println("Ошибка при сохранении: " + ex.getMessage());
        }
    }

    public void loadFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Calculator loadedCalculator = (Calculator) ois.readObject();
            this.x = loadedCalculator.x;
            this.y = loadedCalculator.y;
            System.out.println("Загружено из файла: " + filename);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Ошибка при загрузке: " + ex.getMessage());
        }
    }

    public void displayState() {
        System.out.printf("x = %.4f, y = %.4f \n", x, y);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();
        String fileName = "Text.txt";

        while (true) {
            System.out.println("Введите число x (save, load или exit):");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            } else if (input.equalsIgnoreCase("save")) {
                calculator.saveFile(fileName);
            } else if (input.equalsIgnoreCase("load")) {
                calculator.loadFile(fileName);
                calculator.displayState();
            } else {
                try {
                    double value = Double.parseDouble(input);
                    calculator.calculate(value);
                    calculator.displayState();
                } catch (NumberFormatException ex) {
                    System.out.println("Пожалуйста, введите число или одну из команд (save, load, exit).");
                }
            }
        }
    }
}
