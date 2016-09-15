[Description]

In AES.java, the program reads the key and input text from file. extendKey function extends the cipher key to 240 byte as specified in the following page: http://www.samiam.org/key-schedule.html
The details of the function are specified in the comments, but mainly it performs rotation, substitution, rcon and xor again and again to get the
extended key.

Then the program decided whether it is going to encrypt or decrypt based on user input.

It reads a line from input (handles if the input is not well formed by padding with 0 or truncating), then it performs the AES operations 
(subBytes, shiftRows, mixColumns, and addRoundKey) again and again in correct order(depends on whether it is encryption or decryption) to get the
cipher text/recover plain text.

s-box, inverse s-box and rcon lookup tables are from the internet, and mixcolumn was already implemented.
 
To compile the program, you need to use "javac *.java". To run it, you need to use "java AES option [keyFile] [inputFile] "


The only known bug is in the method for key scheduling. 
All other steps are (encryption( including sub steps: subByte, shiftRows, MixColumns, addRoundKey) and decryption with all substeps ) are working correctly.

The program correctly encodes and decodes inputs using AES steps, however uses a different extended key due to bug above . (let's call it property :) )


[Test Case 1]

[Command line]
java AES e key input
java AES d key input.enc

[Timing Information]
1743 ms for encryption
1838 ms for decryption

[Input Filenames]
key and input for encryption
key and input.enc for decryption

[Output Filenames]
input.enc for encryption
input.enc.dec for decryption


[Test Case 2]	different input file with same key

[Command line]
java AES e key input2
java AES d key input2.enc

[Timing Information]
1825 ms for encryption
1953 ms for decryption

[Input Filenames]
key and input2 for encryption
key and input2.enc for decryption

[Output Filenames]
input2.enc for encryption
input2.enc.dec for decrypt


[Test Case 3] same input1 file with different key

[Command line]
java AES e key2 input3
java AES d key2 input3.enc

[Timing Information]
1748 ms for encryption
1756 ms for decryption

[Input Filenames]
key2 and input3 for encryption
key2 and input3.enc for decryption

[Output Filenames]
input3.enc for encryption
input3.enc.dec for decrypt

[Test Case 4]  input2 file with different key (key2)

[Command line]
java AES e key2 input4
java AES d key2 input4.enc

[Timing Information]
1839 ms for encryption
1872 ms for decryption

[Input Filenames]
key2 and input4 for encryption
key2 and input4.enc for decryption

[Output Filenames]
input4.enc for encryption
input4.enc.dec for decrypt



