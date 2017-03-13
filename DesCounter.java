//package edu.binghamton.security;

import java.net.*;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.*;

public class DesCounter {
	
	public static void main(String[] argv) throws Exception 
	{
		/*
		Initailizing Input File
		*/
		String inputFileName = argv[0];
		FileInputStream inputFromFile = new FileInputStream(inputFileName);
		
		/*
		Initailizing Output File
		*/
		String outputFileName = argv[1];
		String outputFilePath = getCurrentDirectory().concat("/".concat(outputFileName));		
		File outputFile = new File(outputFilePath);
		FileOutputStream outputToFile = new FileOutputStream(outputFileName);

		/*
		Initializing Counter, Encryption key and Mode
		*/
		int mode = Integer.parseInt(argv[2]);
		String counter = "00001234";
		SecretKey key = getSecretKey();
		DesEncrypter encrypter = new DesEncrypter(key);
		int plaintextBlocks = 0;
		int characterCount = 0;
		
		if (mode == 1)
	    {
	    /*
		Encryption Mode
		*/
	    	try 
	    	{
				/*
				byte Array for strong 8 bytes of input file read.
				*/
				byte[] data = new byte[8]; 
				int readCount;
				int offset = 0;
				
				while ((readCount = inputFromFile.read(data, 0, 8)) != -1) 
				{
					/*
						Size read is 8 bytes.
					*/
					if (readCount == 8 && offset == 0) 
					{
						/*
						Byte Array to store 8 bytes of Secretkey and cipher text.
						*/
						byte[]  encryptedKey = encrypter.encryptCounter(counter);
						byte [] cipher = new byte[readCount];
						plaintextBlocks+=1;
						characterCount += readCount;
						/*
						Encrypting Plaintext byte by byte with encrypted key.
						*/
						for (int i = 0; i < readCount; i++ )
						{
							cipher[i] = (byte)(encryptedKey[i] ^ data[i]);
							outputToFile.write(cipher[i]);
						}
						//outputToFile.write(cipher);
						counter = incrementCounter(counter);
					}
					/*
						Size read is less than 8 bytes.
					*/
					else if (readCount < 8 && readCount > 0 && offset == 0)
					 {
						/*
						Byte Array to store required bytes of Secretkey and cipher text.
						*/
						byte [] encryptedKey = encrypter.encryptCounter(counter);
						byte [] cipher = new byte[readCount];
						plaintextBlocks+=1;
						characterCount += readCount;
						/*
						Encrypting Plaintext byte by byte with encrypted key.
						*/
						for (int i = 0; i < readCount; i++ )
						{
							cipher[i] = (byte)(encryptedKey[i] ^ data[i]);
							outputToFile.write(cipher[i]);
						}
						//outputToFile.write(cipher);
						counter = incrementCounter(counter);
					}
				}
			} 
			catch (IOException e) 
			{
				System.out.println("Error occurred while reading from File " + e);
			}
		}
		else if (mode == 0)
		{
		/*
		Decryption Mode
		*/
			try 
			{
				byte[] data = new byte[8]; 
			
				int readCount;
				int offset = 0;
				
				while ((readCount = inputFromFile.read(data, 0, 8)) != -1) 
				{
					/*
						Size read is 8 bytes.
					*/
					if (readCount == 8 && offset == 0) 
					{
						/*
						Byte Array to store 8 bytes of Secretkey and cipher text.
						*/
						byte[]  encryptedKey = encrypter.encryptCounter(counter);
						byte [] cipher = new byte[readCount];
						plaintextBlocks+=1;
						characterCount += readCount;
						/*
						Encrypting Plaintext byte by byte with encrypted key.
						*/
						for (int i = 0; i < readCount; i++ )
						{
							cipher[i] = (byte)(encryptedKey[i] ^ data[i]);
							outputToFile.write(cipher[i]);
						}
						//outputToFile.write(cipher);
						counter = incrementCounter(counter);
					}
					/*
						Size read is less than 8 bytes.
					*/
					else if (readCount < 8 && readCount > 0 && offset == 0)
					 {
						/*
						Byte Array to store required bytes of Secretkey and cipher text.
						*/
						byte [] encryptedKey = encrypter.encryptCounter(counter);
						byte [] cipher = new byte[readCount];
						plaintextBlocks+=1;
						characterCount += readCount;
						/*
						Encrypting Plaintext byte by byte with encrypted key.
						*/
						for (int i = 0; i < readCount; i++ )
						{
							cipher[i] = (byte)(encryptedKey[i] ^ data[i]);
							outputToFile.write(cipher[i]);
						}
						//outputToFile.write(cipher);
						counter = incrementCounter(counter);
					}
				}
			}
			catch (IOException e) 
			{
				System.out.println("Error occurred while reading from File " + e);
			}
		}
		if (mode == 1)
		{	
			System.out.println("PlainText blocks encrypted: " + plaintextBlocks);
			System.out.println("character Count in File: " + characterCount);
		}
		else if (mode == 0)
		{	
			System.out.println("CipherText blocks Decrypted: " + plaintextBlocks);
			System.out.println("character Count in File: " + characterCount);
		}
	}
	
	/*
	Function to generate SecretKey to encrypt Counter
	*/
	public static SecretKey getSecretKey()
	{
		byte[] raw = new byte[]{0x01, 0x72, 0x43, 0x3E, 0x1C, 0x7A, 0x55};
		byte[] keyBytes = addParity(raw);
		SecretKey key = new SecretKeySpec(keyBytes, "DES");
		return key;
	}
	
	/*
	Function to add parity bits to RAW DES Key
	*/
	public static byte[] addParity(byte[] in) 
	{
		byte[] result = new byte[8];
		int resultIx = 1;
		int bitCount = 0;
		for (int i = 0; i < 56; i++) 
		{
			boolean bit = (in[6 - i / 8] & (1 << (i % 8))) > 0;
			if (bit) 
			{
				result[7 - resultIx / 8] |= (1 << (resultIx % 8)) & 0xFF;
				bitCount++;
			}
			if ((i + 1) % 7 == 0) 
			{
				if (bitCount % 2 == 0) 
				{
					result[7 - resultIx / 8] |= 1;
				}
					resultIx++;
					bitCount = 0;
			}
			resultIx++;
		}
		return result;
	}

	/*
	Function for incrementing Counter by 1 and reset when 99999999
	*/
	public static String incrementCounter(String counter) 
	{
		String result = null;
		Integer counterValue = Integer.parseInt(counter);
		if (counterValue == 99999999)
		{
			counterValue = 0;
		}
		else
		{
			counterValue = counterValue + 1;
		}
		result = counterValue.toString();
		if (result.length() < 8) 
		{
			while (result.length() != 8) 
			{
				result = "0".concat(result);
			}
		}
		//System.out.println(result);
		return result;
	}

	/*
	Function to Get Current working directory.
	*/
	public static String getCurrentDirectory() 
	{
		String current = System.getProperty("user.dir");
		return current;
	}
}

