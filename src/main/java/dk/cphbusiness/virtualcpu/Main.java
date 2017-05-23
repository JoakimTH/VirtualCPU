package dk.cphbusiness.virtualcpu;


import java.util.Scanner;

public class Main {
  
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String input = "";
    
    System.out.println("Welcome to the awesome CPU program");
    Program factorial = new Program("01001010", "00010000", "00001100", "11000110", "00010010","00001111","00110010",
            "00000111","10001100","01000010","00100001","00011000","00010000","00010111","00010000","00001100",
            "11000110","00010011","00010010","00000010","00100001","00011000");
    
    Program tailFactorial = new Program(
            "01000010", //MOV 1 A
            "00010000", //PUSH A
            "01001010", //MOV 5 A
            "00010000", //PUSH A
            "00001100", //ALWAYS
            "11001000", //CALL #8
            "00010010", //POP A
            "00001111", //HALT
            "00110010", //MOV +1 A
            "00000111", //NZERO
            "10001100", //JMP #12
            "00011001", //RTN +1
            "00110101", //MOV +2 B
            "00000010", //MUL
            "00100010", //MOV A +2
            "00110010", //MOV +1 A
            "00010111", //DEC
            "00100001", //MOV A +1
            "00001100", //ALWAYS
            "10001000"); //JMP #8
    Machine machine = new Machine();
    machine.load(tailFactorial);
    machine.init();
    
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

      
    //machine.print(System.out);
    //for (int line : factorial) System.out.println(">>> "+line);
    
    }
    
  }
