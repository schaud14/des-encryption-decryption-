# des-encryption-decryption-

In this assignment, you will take an arbitrarily sized input file and encrypt/decrypt it, by
implementing the counter mode of operation. You can use the code at http://
www.java2s.com/Code/Java/Security/EncryptingaStringwithDES.htm to encrypt the
counter. You can define your own functions if necessary.

1.Your code should result in an executable of the following form:
java DesCounter <inputfile> <outputfile> <ENC/DEC>
The parameters description is as follows:
• <Inputfile>: input file name
• <outputfile>: output file name
• <ENC/DEC>: 1/0 – encryption or decryption

For example:
java DesCounter test.txt test.des 1 //encrypt test.txt and store the encrypted content in
file test.des
java DesCounter test.des out 0 //decrypt test.des and store the decrypted content in file
out

2. The key will be hard coded in your program as follows:
byte[] raw = new byte[]{0x01, 0x72, 0x43, 0x3E, 0x1C, 0x7A, 0x55};
You can use the code at http://tomcov2.googlecode.com/svn-history/r20/tomcov/src/
com/tomcov/server/Cryptage.java to convert raw to DES secrete key.
The counter is represented as a string comprising 8 digits (0-9). The initial counter
value is “00001234”.

3. Your program will process 8-byte plaintext (i.e., the size of the plaintext block is 64
bits) at a time, i.e., compute xor of the 8-byte plaintext with the first 8 bytes of the
decrypted counter

4. The input file contains only letters a-z (no space, \n, or \t), Your program should print
the number of characters in the input file and the number of plaintext blocks.
