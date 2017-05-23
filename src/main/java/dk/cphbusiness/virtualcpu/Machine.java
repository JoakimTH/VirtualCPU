package dk.cphbusiness.virtualcpu;

import java.io.PrintStream;

public class Machine {

    private Cpu cpu = new Cpu();
    private Memory memory = new Memory();

    public void load(Program program) {
        int index = 0;
        for (int instr : program) {
            memory.set(index++, instr);
        }
    }

    public void tick() {
        int instr = memory.get(cpu.getIp());
        if (instr == 0b0000_0000) {
            // 0000 0000  NOP
            cpu.incIp();
            // cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0001) {
            // 0000 0001 ADD A B
            cpu.setA(cpu.getA() + cpu.getB());
            cpu.incIp();
        } // ..
        else if ((instr & 0b1111_0000) == 0b0010_0000) {
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++

            // 0010 1 011 MOV B (=1) +3  [SP +3] // Move register B to memory position of SP with offset 3
            // 00101011 finding instruction
            //    and
            // 11110000
            // --------
            // 00100000
            // 00101011 finding offset
            //    and
            // 00000111
            // --------
            // 00000011 = 3
            // 00101011 finding register
            //    and
            // 00001000
            // --------
            // 00001000 = 8
            //    >> 3
            // 00000001 = 1
            int o = instr & 0b0000_0111;
            int r = (instr & 0b0000_1000) >> 3;
            if (r == cpu.A) {
                memory.set(cpu.getSp() + o, cpu.getA());
            } else {
                memory.set(cpu.getSp() + o, cpu.getB());
            }
            cpu.setIp(cpu.getIp() + 1);
        } // MOV v r
        else if ((instr & 0b1100_0000) == 0b0100_0000) {
            int v = (instr & 0b0011_1110) >> 1;
            int r = instr & 0b0000_0001;
            if (r == 1) {
                cpu.setB(v);
            } else {
                cpu.setA(v);
            }
            cpu.incIp();
        } //PUSH r
        else if ((instr & 0b1111_1110) == 0b0001_0000) {
            int r = instr & 0b0000_0001;
            cpu.decSp();
            if (r==1){
                memory.set(cpu.getSp(), cpu.getB());
            } else {
                memory.set(cpu.getSp(),cpu.getA());
            }
            cpu.incIp();
        } //ALWAYS
        else if (instr == 0b0000_1100) {
            cpu.setFlag(true);
            cpu.incIp();
        } //CALL #a
        else if ((instr & 0b1100_0000) == 0b1100_0000) {
            if (cpu.isFlag()) {
                int a = instr & 0b0011_1111;
                cpu.decSp();
                memory.set(cpu.getSp(),cpu.getIp() );
                cpu.setIp(a);
            } else {
                cpu.incIp();
            }
        } //POP r
        else if ((instr & 0b1111_1110) == 0b0001_0010) {
            int r = instr & 0000_0001;            
            if (r == 1) {
                //memory.set(cpu.getSp()+1 , cpu.getB());
                cpu.setB(memory.get(cpu.getSp()));
                cpu.incSp();
            } else {             
                cpu.setA(memory.get(cpu.getSp()));
                cpu.incSp();
            }
            cpu.incIp();
        } 
        //HALT
        else if (instr == 0b0000_1111) {
            System.out.println("HALT EXECUTION");
        //HALT CODE
        } 
        //MOV o r
        else if ((instr & 0b1111_0000) == 0b0011_0000) {
            int r = instr & 0b0000_0001;
            int o = (instr & 0b0000_1110) >> 1;
            if (r == 1) {
                cpu.setB(memory.get(cpu.getSp()+o));
            } else {
                cpu.setA(memory.get(cpu.getSp()+o));
            }
            cpu.incIp();
        } 
        //NZERO
        else if (instr == 0b0000_0111) {
            if (cpu.getA() != 0) {
                cpu.setFlag(true);
            } else {
                cpu.setFlag(false);
            }
            cpu.incIp();
        } 
        // JMP #a
        else if ((instr & 0b1100_0000) == 0b1000_0000) {
            if (cpu.isFlag()) {
                int a = instr & 0b0011_1111;
                cpu.setIp(a);
            } else {
                cpu.incIp();
            }
        }
        //RTN +o
        else if((instr & 0b1111_1000) == 0b0001_1000){
            int o = instr & 0b0000_0111;
            cpu.setIp(memory.get(cpu.getSp()));
            cpu.incSp();
            //memory.set(cpu.getSp() + 1, cpu.getIp());
            cpu.setSp(cpu.getSp()+o);
            
            cpu.incIp();
        }
        //DEC
        else if(instr == 0b0001_0111){
            cpu.setA(cpu.getA()-1);
            cpu.incIp();
        }
        //MUL
        else if(instr == 0b0000_0010){
            cpu.setA(cpu.getA() * cpu.getB());
            cpu.incIp();
        }
    }

    public void print(PrintStream out) {
        memory.print(out);
        out.println("-------------");
        cpu.print(out);
    }

}
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
