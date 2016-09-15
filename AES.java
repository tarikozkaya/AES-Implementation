
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tarik
 */
public class AES {
    
    static File fileText;
    static FileWriter fw2;
    static BufferedWriter bw2;
    static boolean initialized = false;

    static char sbox[] = {
        //0     1    2      3     4    5     6     7      8    9     A      B    C     D     E     F
        0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76, //0
        0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0, //1
        0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15, //2
        0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75, //3
        0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84, //4
        0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf, //5
        0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8, //6
        0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2, //7
        0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73, //8
        0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb, //9
        0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79, //A
        0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08, //B
        0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a, //C
        0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e, //D
        0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf, //E
        0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}; //F

    static char rsbox[]
            = {0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb, 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb, 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e, 0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25, 0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92, 0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84, 0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06, 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b, 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73, 0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e, 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b, 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4, 0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f, 0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef, 0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61, 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d};

    static char Rcon[] = {
        0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
        0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
        0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
        0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d,
        0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab,
        0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d,
        0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25,
        0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01,
        0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d,
        0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa,
        0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a,
        0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02,
        0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
        0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
        0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
        0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
        0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f,
        0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5,
        0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33,
        0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb};

    public static void main(String args[]) throws Exception {
        char plainText[][] = new char[4][4];	// "00000000000000000000000000000000";
        char cipherKey[][] = new char[4][8];	// "00000000000000000000000000000000";

        long startTime = System.currentTimeMillis();
        
        String option = args[0];
        String keyFile = args[1];
        String inputFile = args[2];
        
//        String option = option = "d";
//        String keyFile = "extended";
//        String inputFile = "plain.enc";
        
        
        BufferedReader reader3 = new BufferedReader(new FileReader(keyFile));
        
   //     for (int c = 0; c < 4; c++) {
            
            String line3 = reader3.readLine();

            while(line3.length() < 32)
                line3 += "0";
            
            if(line3.length() > 32)
                line3 = line3.substring(0, 32);
            
          //  System.out.println(line3 + " len: " + line3.length());
            
            char temp3[] = line3.toCharArray();
            
            int c4 = 0;
            for (int c2 = 0; c2 < 32; c2 += 2) {
                cipherKey[c4 % 4][c4++ / 4] = (char)Integer.parseInt("" + temp3[c2] + temp3[c2+1], 16);
            }

    //    }
        reader3.close();
        System.out.println("Cipher key:");
        printMatrix(cipherKey, 4, 4);
        
        // print 2D array for debugging
     //   printMatrix(cipherKey, 4, 8);

     //   System.out.println("Rcon " + Integer.toHexString((int) getRconValue((char) 5)));
     //   System.out.println("Plain Text: ");
        char extendedKey[][] = new char[4][60];
        extendedKey = extendKey(cipherKey);
        
        System.out.println("Extended key:");
        printMatrix(extendedKey, 4, 40);
//        BufferedReader reader = new BufferedReader(new FileReader(keyFile));
//        String line = reader.readLine();
//
//        for (int c = 0; c < 4; c++) {
//            Scanner scan = new Scanner(line);
//
//            for (int c2 = 0; c2 < 60; c2++) {
//                extendedKey[c][c2] = (char) scan.nextInt(16);
//            }
//
//            line = reader.readLine();
//        }
//        reader.close();

        BufferedReader reader2 = new BufferedReader(new FileReader(inputFile));
        
   //     for (int c = 0; c < 4; c++) {
            
        String line2 = reader2.readLine();
        while (line2 != null) 
        {
            System.out.println("Working on line: "+ line2);
            while (line2.length() < 32) {
                line2 += "0";
            }

            if (line2.length() > 32) {
                line2 = line2.substring(0, 32);
            }

            //      System.out.println(line2 + " len: " + line2.length());
            char temp2[] = line2.toCharArray();
            
            int c3 = 0;
            for (int c2 = 0; c2 < 32; c2 += 2) {
                plainText[c3 % 4][c3++ / 4] = (char)Integer.parseInt("" + temp2[c2] + temp2[c2+1], 16);
            }

    //    }
        System.out.println("Input text: ");
        printMatrix(plainText, 4, 4);
        
        if ( option.equalsIgnoreCase("e"))
        {
            //all rounds are here
            for (int c = 0; c < 15; c++) {

                for (int c2 = 0; c2 < 4; c2++) {
                    plainText[0][c2] ^= extendedKey[0][c * 4 + c2];
                    plainText[1][c2] ^= extendedKey[1][c * 4 + c2];
                    plainText[2][c2] ^= extendedKey[2][c * 4 + c2];
                    plainText[3][c2] ^= extendedKey[3][c * 4 + c2];
                }

                System.out.println("After addRoundKey(" + c + "):");
                printPlainText(plainText, 4, 4);

                for (int c2 = 0; c2 < 4; c2++) {
                    
                    plainText[0][c2] = getSBoxValue(plainText[0][c2]);
                    plainText[1][c2] = getSBoxValue(plainText[1][c2]);
                    plainText[2][c2] = getSBoxValue(plainText[2][c2]);
                    plainText[3][c2] = getSBoxValue(plainText[3][c2]);
            }

            System.out.println("After subBytes:");
            printPlainText(plainText, 4, 4);

            for (int c2 = 0; c2 < 4; c2++) {
                char temp[] = {plainText[c2][0], plainText[c2][1], plainText[c2][2], plainText[c2][3]};
                plainText[c2][(0 + 4 - c2) % 4] = temp[0];
                plainText[c2][(1 + 4 - c2) % 4] = temp[1];
                plainText[c2][(2 + 4 - c2) % 4] = temp[2];
                plainText[c2][(3 + 4 - c2) % 4] = temp[3];
            }

            System.out.println("After shiftRows:");
            printPlainText(plainText, 4, 4);

            if (c == 13) {
                break;
            }

            mixColumn2(0, plainText);
            mixColumn2(1, plainText);
            mixColumn2(2, plainText);
            mixColumn2(3, plainText);

            System.out.println("After mixColumns:");
            printPlainText(plainText, 4, 4);
        }
        for (int c2 = 0; c2 < 4; c2++) {
            plainText[0][c2] ^= extendedKey[0][14 * 4 + c2];
            plainText[1][c2] ^= extendedKey[1][14 * 4 + c2];
            plainText[2][c2] ^= extendedKey[2][14 * 4 + c2];
            plainText[3][c2] ^= extendedKey[3][14 * 4 + c2];
        }

        System.out.println("After addRoundKey(14):");
        printPlainText(plainText, 4, 4);
        
        //encryption is complete!
            writeMatrix(plainText, 4, 4, inputFile + ".enc");
        
        }
        else
        {
            System.out.println("Starting Decryption: ");

            for (int c2 = 0; c2 < 4; c2++) {
                plainText[0][c2] ^= extendedKey[0][14 * 4 + c2];
                plainText[1][c2] ^= extendedKey[1][14 * 4 + c2];
                plainText[2][c2] ^= extendedKey[2][14 * 4 + c2];
                plainText[3][c2] ^= extendedKey[3][14 * 4 + c2];
            }

            System.out.println("After addRoundKey(14):");
            printPlainText(plainText, 4, 4);

            for (int c = 13; c >= 0; c--) {

                for (int c2 = 0; c2 < 4; c2++) {
                    char temp[] = {plainText[c2][0], plainText[c2][1], plainText[c2][2], plainText[c2][3]};
                    plainText[c2][(0 + c2) % 4] = temp[0];
                    plainText[c2][(1 + c2) % 4] = temp[1];
                    plainText[c2][(2 + c2) % 4] = temp[2];
                    plainText[c2][(3 + c2) % 4] = temp[3];
                }

                System.out.println("After invShiftRows:");
                printPlainText(plainText, 4, 4);

                for (int c2 = 0; c2 < 4; c2++) {
                    plainText[0][c2] = getSBoxInvert(plainText[0][c2]);
                    plainText[1][c2] = getSBoxInvert(plainText[1][c2]);
                    plainText[2][c2] = getSBoxInvert(plainText[2][c2]);
                    plainText[3][c2] = getSBoxInvert(plainText[3][c2]);
                }

                System.out.println("After invSubBytes:");
                printPlainText(plainText, 4, 4);

                for (int c2 = 0; c2 < 4; c2++) {
                    plainText[0][c2] ^= extendedKey[0][c * 4 + c2];
                    plainText[1][c2] ^= extendedKey[1][c * 4 + c2];
                    plainText[2][c2] ^= extendedKey[2][c * 4 + c2];
                    plainText[3][c2] ^= extendedKey[3][c * 4 + c2];
                }

                System.out.println("After addRoundKey(" + c + "):");
                printPlainText(plainText, 4, 4);                
                
                if (c == 0) {                                    
                //decryption is complete!
                writeMatrix(plainText, 4, 4, inputFile + ".dec");
                break;
                }

                invMixColumn2(0, plainText);
                invMixColumn2(1, plainText);
                invMixColumn2(2, plainText);
                invMixColumn2(3, plainText);

                System.out.println("After invMixColumns:");
                printPlainText(plainText, 4, 4);

            }
         }
        line2 = reader2.readLine();
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        reader2.close();
        bw2.close();
    }
    
    public static void writeMatrix(char [][] matrix, int row, int column, String fileName) throws Exception
    {
        fileText = new File(fileName);

        // if file doesnt exists, then create it
        if (!fileText.exists()) {
            fileText.createNewFile();
        }
        String out = "";
        
        for (int c = 0; c < row; c++) 
            for (int c2 = 0; c2 < column; c2++) {
                out += String.format("%02X", (int) matrix[c2][c]);
            }
        
        if (! initialized)
        {
            fw2 = new FileWriter(fileText.getAbsoluteFile());
            bw2 = new BufferedWriter(fw2);
            initialized = true;
        }
        
        bw2.write(out);
        bw2.newLine();
    }

    public static void printPlainText(char text[][], int row, int column) {
        for (int c = 0; c < row; c++) {

            for (int c2 = 0; c2 < column; c2++) {
                System.out.print("" + String.format("%02X", (int) text[c2][c]) + "");
            }

        }
        System.out.println("");
    }

    public static char[][] extendKey(char key[][]) {
        char extended[][] = new char[4][60];

        for (int c = 0; c < 4; c++) {
            for (int c2 = 0; c2 < 8; c2++) {
                extended[c][c2] = key[c][c2];
            }
        }

        char c = 1;
        for (int i = 7; i < 55; i++) {

            // STEP 1
            // rotate
      //      System.out.println("c: " + (int) c + " i: " + i + " rot 4 byte: ");
       //     System.out.println("" + String.format("%02X", (int) extended[0][i]) + " " + String.format("%02X", (int) extended[1][i]) + " " + String.format("%02X", (int) extended[2][i]) + " " + String.format("%02X", (int) extended[3][i]));
            char tempArr[] = new char[4];
            tempArr[0] = extended[1][i];
            tempArr[1] = extended[2][i];
            tempArr[2] = extended[3][i];
            tempArr[3] = extended[0][i];

         //   System.out.println("After rotation" + String.format("%02X", (int) tempArr[0]) + " " + String.format("%02X", (int) tempArr[1]) + " " + String.format("%02X", (int) tempArr[2]) + " " + String.format("%02X", (int) tempArr[3]));

            //substitute
            tempArr[0] = getSBoxValue(tempArr[0]);
            tempArr[1] = getSBoxValue(tempArr[1]);
            tempArr[2] = getSBoxValue(tempArr[2]);
            tempArr[3] = getSBoxValue(tempArr[3]);

         //   System.out.println("After sub" + String.format("%02X", (int) tempArr[0]) + " " + String.format("%02X", (int) tempArr[1]) + " " + String.format("%02X", (int) tempArr[2]) + " " + String.format("%02X", (int) tempArr[3]));

            char rCon = getRconValue(c);
            c = (char) (c + 1);
            tempArr[0] ^= rCon;

            extended[0][i + 1] = (char) (extended[0][i - 3] ^ tempArr[0]);
            extended[1][i + 1] = (char) (extended[1][i - 3] ^ tempArr[1]);
            extended[2][i + 1] = (char) (extended[2][i - 3] ^ tempArr[2]);
            extended[3][i + 1] = (char) (extended[3][i - 3] ^ tempArr[3]);

            //debugging point
            // System.out.println("c: " + (int)c );
     //       System.out.println("new 4 byte: ");
       //     System.out.println("" + String.format("%02X", (int) extended[0][i + 1]) + " " + String.format("%02X", (int) extended[1][i + 1]) + " " + String.format("%02X", (int) extended[2][i + 1]) + " " + String.format("%02X", (int) extended[3][i + 1]));

            // STEP 2
            for (int c2 = 0; c2 < 3; c2++) {
                i++;
                extended[0][i + 1] = (char) (extended[0][i - 3] ^ extended[0][i]);
                extended[1][i + 1] = (char) (extended[1][i - 3] ^ extended[1][i]);
                extended[2][i + 1] = (char) (extended[2][i - 3] ^ extended[2][i]);
                extended[3][i + 1] = (char) (extended[3][i - 3] ^ extended[3][i]);
            }

            //STEP 3
            i++;
            tempArr[0] = extended[0][i];
            tempArr[1] = extended[1][i];
            tempArr[2] = extended[2][i];
            tempArr[3] = extended[3][i];

//            System.out.println(" step3 begins" + String.format("%02X", (int) tempArr[0]) + " " + String.format("%02X", (int) tempArr[1]) + " " + String.format("%02X", (int) tempArr[2]) + " " + String.format("%02X", (int) tempArr[3]));

            //substitute
            tempArr[0] = getSBoxValue(tempArr[0]);
            tempArr[1] = getSBoxValue(tempArr[1]);
            tempArr[2] = getSBoxValue(tempArr[2]);
            tempArr[3] = getSBoxValue(tempArr[3]);

        //    System.out.println("After step3 sub- i: " + i + " " + String.format("%02X", (int) tempArr[0]) + " " + String.format("%02X", (int) tempArr[1]) + " " + String.format("%02X", (int) tempArr[2]) + " " + String.format("%02X", (int) tempArr[3]));
//                
//                extended[0][i + 1] = (char) (extended[0][i - 3] ^ tempArr[0]);
//                extended[1][i + 1] = (char) (extended[1][i - 3] ^ tempArr[1]);
//                extended[2][i + 1] = (char) (extended[2][i - 3] ^ tempArr[2]);
//                extended[3][i + 1] = (char) (extended[3][i - 3] ^ tempArr[3]);

            extended[0][i + 1] = (char) tempArr[0];
            extended[1][i + 1] = (char) tempArr[1];
            extended[2][i + 1] = (char) tempArr[2];
            extended[3][i + 1] = (char) tempArr[3];

            // STEP 4
            for (int c2 = 0; c2 < 3; c2++) {
                i++;
                extended[0][i + 1] = (char) (extended[0][i - 3] ^ extended[0][i]);
                extended[1][i + 1] = (char) (extended[1][i - 3] ^ extended[1][i]);
                extended[2][i + 1] = (char) (extended[2][i - 3] ^ extended[2][i]);
                extended[3][i + 1] = (char) (extended[3][i - 3] ^ extended[3][i]);
            }

        }

        return extended;
    }

    public static void printMatrix(char[][] matrix, int row, int column) {
        for (int c = 0; c < row; c++) {
            for (int c2 = 0; c2 < column; c2++) {
                System.out.print("" + String.format("%02X", (int) matrix[c][c2]) + " ");
            }
            System.out.println();
        }
    }

    public static char getSBoxValue(char num) {
        //System.out.print("stable[" + Integer.toHexString(num) +"]: " + Integer.toHexString((int)sbox[num]));
        return sbox[num];
    }

    public static char getSBoxInvert(char num) {
        return rsbox[num];
    }

    public static char getRconValue(char num) {
        return Rcon[num];
    }

    ////////////////////////  the mixColumns Tranformation ////////////////////////
    final static int[] LogTable = {
        0, 0, 25, 1, 50, 2, 26, 198, 75, 199, 27, 104, 51, 238, 223, 3,
        100, 4, 224, 14, 52, 141, 129, 239, 76, 113, 8, 200, 248, 105, 28, 193,
        125, 194, 29, 181, 249, 185, 39, 106, 77, 228, 166, 114, 154, 201, 9, 120,
        101, 47, 138, 5, 33, 15, 225, 36, 18, 240, 130, 69, 53, 147, 218, 142,
        150, 143, 219, 189, 54, 208, 206, 148, 19, 92, 210, 241, 64, 70, 131, 56,
        102, 221, 253, 48, 191, 6, 139, 98, 179, 37, 226, 152, 34, 136, 145, 16,
        126, 110, 72, 195, 163, 182, 30, 66, 58, 107, 40, 84, 250, 133, 61, 186,
        43, 121, 10, 21, 155, 159, 94, 202, 78, 212, 172, 229, 243, 115, 167, 87,
        175, 88, 168, 80, 244, 234, 214, 116, 79, 174, 233, 213, 231, 230, 173, 232,
        44, 215, 117, 122, 235, 22, 11, 245, 89, 203, 95, 176, 156, 169, 81, 160,
        127, 12, 246, 111, 23, 196, 73, 236, 216, 67, 31, 45, 164, 118, 123, 183,
        204, 187, 62, 90, 251, 96, 177, 134, 59, 82, 161, 108, 170, 85, 41, 157,
        151, 178, 135, 144, 97, 190, 220, 252, 188, 149, 207, 205, 55, 63, 91, 209,
        83, 57, 132, 60, 65, 162, 109, 71, 20, 42, 158, 93, 86, 242, 211, 171,
        68, 17, 146, 217, 35, 32, 46, 137, 180, 124, 184, 38, 119, 153, 227, 165,
        103, 74, 237, 222, 197, 49, 254, 24, 13, 99, 140, 128, 192, 247, 112, 7};

    final static int[] AlogTable = {
        1, 3, 5, 15, 17, 51, 85, 255, 26, 46, 114, 150, 161, 248, 19, 53,
        95, 225, 56, 72, 216, 115, 149, 164, 247, 2, 6, 10, 30, 34, 102, 170,
        229, 52, 92, 228, 55, 89, 235, 38, 106, 190, 217, 112, 144, 171, 230, 49,
        83, 245, 4, 12, 20, 60, 68, 204, 79, 209, 104, 184, 211, 110, 178, 205,
        76, 212, 103, 169, 224, 59, 77, 215, 98, 166, 241, 8, 24, 40, 120, 136,
        131, 158, 185, 208, 107, 189, 220, 127, 129, 152, 179, 206, 73, 219, 118, 154,
        181, 196, 87, 249, 16, 48, 80, 240, 11, 29, 39, 105, 187, 214, 97, 163,
        254, 25, 43, 125, 135, 146, 173, 236, 47, 113, 147, 174, 233, 32, 96, 160,
        251, 22, 58, 78, 210, 109, 183, 194, 93, 231, 50, 86, 250, 21, 63, 65,
        195, 94, 226, 61, 71, 201, 64, 192, 91, 237, 44, 116, 156, 191, 218, 117,
        159, 186, 213, 100, 172, 239, 42, 126, 130, 157, 188, 223, 122, 142, 137, 128,
        155, 182, 193, 88, 232, 35, 101, 175, 234, 37, 111, 177, 200, 67, 197, 84,
        252, 31, 33, 99, 165, 244, 7, 9, 27, 45, 119, 153, 176, 203, 70, 202,
        69, 207, 74, 222, 121, 139, 134, 145, 168, 227, 62, 66, 198, 81, 243, 14,
        18, 54, 90, 238, 41, 123, 141, 140, 143, 138, 133, 148, 167, 242, 13, 23,
        57, 75, 221, 124, 132, 151, 162, 253, 28, 36, 108, 180, 199, 82, 246, 1};

    public static char mul(int a, char b) {
        int inda = (a < 0) ? (a + 256) : a;
        int indb = (b < 0) ? (b + 256) : b;

        if ((a != 0) && (b != 0)) {
            int index = (LogTable[inda] + LogTable[indb]);
            char val = (char) (AlogTable[index % 255]);
            return val;
        } else {
            return 0;
        }
    } // mul

    // In the following two methods, the input c is the column number in
    // your evolving state matrix st (which originally contained 
    // the plaintext input but is being modified).  Notice that the state here is defined as an
    // array of bytes.  If your state is an array of integers, you'll have
    // to make adjustments. 
    public static void mixColumn2(int c, char st[][]) {
        // This is another alternate version of mixColumn, using the 
        // logtables to do the computation.

        char a[] = new char[4];

        // note that a is just a copy of st[.][c]
        for (int i = 0; i < 4; i++) {
            a[i] = st[i][c];
        }

        // This is exactly the same as mixColumns1, if 
        // the mul columns somehow match the b columns there.
        st[0][c] = (char) (mul(2, a[0]) ^ a[2] ^ a[3] ^ mul(3, a[1]));
        st[1][c] = (char) (mul(2, a[1]) ^ a[3] ^ a[0] ^ mul(3, a[2]));
        st[2][c] = (char) (mul(2, a[2]) ^ a[0] ^ a[1] ^ mul(3, a[3]));
        st[3][c] = (char) (mul(2, a[3]) ^ a[1] ^ a[2] ^ mul(3, a[0]));
    } // mixColumn2

    public static void invMixColumn2(int c, char[][] st) {
        char a[] = new char[4];

        // note that a is just a copy of st[.][c]
        for (int i = 0; i < 4; i++) {
            a[i] = st[i][c];
        }

        st[0][c] = (char) (mul(0xE, a[0]) ^ mul(0xB, a[1]) ^ mul(0xD, a[2]) ^ mul(0x9, a[3]));
        st[1][c] = (char) (mul(0xE, a[1]) ^ mul(0xB, a[2]) ^ mul(0xD, a[3]) ^ mul(0x9, a[0]));
        st[2][c] = (char) (mul(0xE, a[2]) ^ mul(0xB, a[3]) ^ mul(0xD, a[0]) ^ mul(0x9, a[1]));
        st[3][c] = (char) (mul(0xE, a[3]) ^ mul(0xB, a[0]) ^ mul(0xD, a[1]) ^ mul(0x9, a[2]));
    } // invMixColumn2

}
