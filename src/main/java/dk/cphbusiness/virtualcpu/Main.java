package dk.cphbusiness.virtualcpu;


import java.util.Scanner;

public class Main {
  
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String input = "";
    input = scan.nextLine();
    System.out.println("Welcome to the awesome CPU program");
    Program factorial = new Program("01001010", "00010000", "00001100", "11000110", "00010010","00001111","00110010",
            "00000111","10001100","01000010","00100001","00011000","00010000","00010111","00010000","00001100",
            "11000110","00010011","00010010","00000010","00100001","00011000");
    
    Program tailFactorial = new Program("01000010","00010000","01001010","00010000","00001100","11001000","00010010",
            "00001111","00110010","00000111","10001100","00011001","00110101","00000010","00100001","00110100","00010111",
    "00100001","00001100","10001000");
    Machine machine = new Machine();
    machine.load(tailFactorial);
    machine.print(System.out);
//Factorial TAIL RECURSIVE
//
//MOV 1 A
//PUSH A
//MOV 5 A
//PUSH A
//ALWAYS
//CALL #8
//POP A
//HALT
//MOV +1 A
//NZERO
//JMP #12
//RTN +1
//MOV +2 B
//MUL
//MOV A +2
//MOV +1 A
//DEC
//MOV A +1
//ALWAYS
//JMP #8
      while (scan.nextLine().equals(input)) {
          machine.tick();
          machine.print(System.out);
      }
    //machine.print(System.out);
    for (int line : factorial) System.out.println(">>> "+line);
    
    }
    
  }
