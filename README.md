# Virtual CPU

Registers and flag:
-------------------

| Name  | Type                | Description                                  |
| :---: | :------------------ | :------------------------------------------- |
| A     | Accumulator         | Primary accumulator                          |
| B     | Accumulator         | Secondary accumulator                        |
| SP    | Stack Pointer       | Points to last element pushed                |
| IP    | Instruction Pointer | Points to next instruction                   |
| F     | Flag                | Decides if next `JMP` or `CALL` is effective |

Arguments:
----------

| Abr.  | Type        | Bits  | Literal                      |
| :---: |:----------- | :---: | :--------------------------- |
| r     | Register    | 1     | `A` (= 0) or `B` (= 1)       |
| o     | Offset      | 3     | `+0` to `+7` Offset to SP    |
| v     | Value       | 5     | `-16` to `15` (2-complement) |
| a     | Address     | 6     | `#0` to `#63`                |


Instructions:
-------------

| Binary      | Code      | Function                                           |
| :---------: | :-------- | :------------------------------------------------- |
| `0000 0000` | `NOP`     | IP++                                               |
| `0000 0001` | `ADD`     | A ← A + B; IP++                                    |
| `0000 0010` | `MUL`     | A ← A*B; IP++                                      |
| `0000 0011` | `DIV`     | A ← A/B; IP++                                      |
| `0000 0100` | `ZERO`    | F ← A = 0; IP++                                    |
| `0000 0101` | `NEG`     | F ← A < 0; IP++                                    |
| `0000 0110` | `POS`     | F ← A > 0; IP++                                    |
| `0000 0111` | `NZERO`   | F ← A ≠ 0; IP++                                    |
| `0000 1000` | `EQ`      | F ← A = B; IP++                                    |
| `0000 1001` | `LT`      | F ← A < B; IP++                                    |
| `0000 1010` | `GT`      | F ← A > B; IP++                                    |
| `0000 1011` | `NEQ`     | F ← A ≠ B; IP++                                    |
| `0000 1100` | `ALWAYS`  | F ← **true**; IP++                                 |
| `0000 1101` |           | *Undefined*                                        |
| `0000 1110` |           | *Undefined*                                        |
| `0000 1111` | `HALT`    | *Halts execution*                                  |
| `0001 000r` | `PUSH r`  | [--SP] ← r; IP++                                   |
| `0001 001r` | `POP r`   | r ← [SP++]; IP++                                   |
| `0001 0100` | `MOV A B` | B ← A; IP++                                        |
| `0001 0101` | `MOV B A` | A ← B; IP++                                        |
| `0001 0110` | `INC`     | A++; IP++                                          |
| `0001 0111` | `DEC`     | A--; IP++                                          |
| `0001 1ooo` | `RTN +o`  | IP ← [SP++]; SP += o; IP++                         |
| `0010 rooo` | `MOV r o` | [SP + o] ← r; IP++                                 |
| `0011 ooor` | `MOV o r` | r ← [SP + o]; IP++                                 |
| `01vv vvvr` | `MOV v r` | r ← v; IP++                                        |
| `10aa aaaa` | `JMP #a`  | **if** F **then** IP ← a **else** IP++             |
| `11aa aaaa` | `CALL #a` | **if** F **then** [--SP] ← IP; IP ← a **else** IP++|


Screen dumps

factorial calculates `5!`
```
 0 => [0100 1010] MOV 5 A               |  32    [0000 0000]   0                 
 1    [0001 0000] PUSH A                |  33    [0000 0000]   0                 
 2    [0000 1100] ALWAYS                |  34    [0000 0000]   0                 
 3    [1100 0110] CALL #6               |  35    [0000 0000]   0                 
 4    [0001 0010] POP A                 |  36    [0000 0000]   0                 
 5    [0000 1111] HALT                  |  37    [0000 0000]   0                 
 6    [0011 0010] MOV +1 A              |  38    [0000 0000]   0                 
 7    [0000 0111] NZERO                 |  39    [0000 0000]   0                 
 8    [1000 1100] JMP #12               |  40    [0000 0000]   0                 
 9    [0100 0010] MOV 1 A               |  41    [0000 0000]   0                 
10    [0010 0001] MOV A +1              |  42    [0000 0000]   0                 
11    [0001 1000] RTN +0                |  43    [0000 0000]   0                 
12    [0001 0000] PUSH A                |  44    [0000 0000]   0                 
13    [0001 0111] DEC                   |  45    [0000 0000]   0                 
14    [0001 0000] PUSH A                |  46    [0000 0000]   0                 
15    [0000 1100] ALWAYS                |  47    [0000 0000]   0                 
16    [1100 0110] CALL #6               |  48    [0000 0000]   0                 
17    [0001 0011] POP B                 |  49    [0000 0000]   0                 
18    [0001 0010] POP A                 |  50    [0000 0000]   0                 
19    [0000 0010] MUL                   |  51    [0000 0000]   0                 
20    [0010 0001] MOV A +1              |  52    [0000 0000]   0                 
21    [0001 1000] RTN +0                |  53    [0000 0000]   0                 
22    [0000 0000]   0                   |  54    [0000 0000]   0                 
23    [0000 0000]   0                   |  55    [0000 0000]   0                 
24    [0000 0000]   0                   |  56    [0000 0000]   0                 
25    [0000 0000]   0                   |  57    [0000 0000]   0                 
26    [0000 0000]   0                   |  58    [0000 0000]   0                 
27    [0000 0000]   0                   |  59    [0000 0000]   0                 
28    [0000 0000]   0                   |  60    [0000 0000]   0                 
29    [0000 0000]   0                   |  61    [0000 0000]   0                 
30    [0000 0000]   0                   |  62    [0000 0000]   0                 
31    [0000 0000]   0                   |  63    [0000 0000]   0                 
-----------------------------------------
A: [0000 0000]   0    | IP: [00 0000]   0   
B: [0000 0000]   0    | SP: [00 0000]   0   
F: false
```


calculus computes `(5 + 11)*-3`
```
 0 => [0100 1010] MOV 5 A               |  32    [0000 0000]   0                 
 1    [0001 0000] PUSH A                |  33    [0000 0000]   0                 
 2    [0101 0110] MOV 11 A              |  34    [0000 0000]   0                 
 3    [0001 0000] PUSH A                |  35    [0000 0000]   0                 
 4    [0111 1010] MOV -3 A              |  36    [0000 0000]   0                 
 5    [0001 0000] PUSH A                |  37    [0000 0000]   0                 
 6    [0000 1100] ALWAYS                |  38    [0000 0000]   0                 
 7    [1100 1010] CALL #10              |  39    [0000 0000]   0                 
 8    [0001 0010] POP A                 |  40    [0000 0000]   0                 
 9    [0000 1111] HALT                  |  41    [0000 0000]   0                 
10    [0011 0111] MOV +3 B              |  42    [0000 0000]   0                 
11    [0011 0100] MOV +2 A              |  43    [0000 0000]   0                 
12    [0000 0001] ADD                   |  44    [0000 0000]   0                 
13    [0011 0011] MOV +1 B              |  45    [0000 0000]   0                 
14    [0000 0010] MUL                   |  46    [0000 0000]   0                 
15    [0010 0011] MOV A +3              |  47    [0000 0000]   0                 
16    [0001 1010] RTN +2                |  48    [0000 0000]   0                 
17    [0000 0000]   0                   |  49    [0000 0000]   0                 
18    [0000 0000]   0                   |  50    [0000 0000]   0                 
19    [0000 0000]   0                   |  51    [0000 0000]   0                 
20    [0000 0000]   0                   |  52    [0000 0000]   0                 
21    [0000 0000]   0                   |  53    [0000 0000]   0                 
22    [0000 0000]   0                   |  54    [0000 0000]   0                 
23    [0000 0000]   0                   |  55    [0000 0000]   0                 
24    [0000 0000]   0                   |  56    [0000 0000]   0                 
25    [0000 0000]   0                   |  57    [0000 0000]   0                 
26    [0000 0000]   0                   |  58    [0000 0000]   0                 
27    [0000 0000]   0                   |  59    [0000 0000]   0                 
28    [0000 0000]   0                   |  60    [0000 0000]   0                 
29    [0000 0000]   0                   |  61    [0000 0000]   0                 
30    [0000 0000]   0                   |  62    [0000 0000]   0                 
31    [0000 0000]   0                   |  63    [0000 0000]   0                 
-----------------------------------------
A: [0000 0000]   0    | IP: [00 0000]   0   
B: [0000 0000]   0    | SP: [00 0000]   0   
F: false
```

