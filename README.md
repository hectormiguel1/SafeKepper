# SafeKepper

          $java SafeKepper --[encrypt/decrypt] filepath 

 	 --encrypt: Asks for 3 passwords and encrypts given filepath with said passwords 
	 
	 --decrypt: Asks for 3 passwords and decrypts the given filepath with said passwords add --yes to re-encrypt the file after
	 use or --no to leave it in plain text.
	 
	 filepath: is the location of the file which will be encrypted or dercrypted 
	 
# THIS PROGRAM HEAVILY RELIES ON GPG, MV, RM, SO IT WILL ONLY WORK ON OS WITH THEM INSTALLED!!!!!


# To use:
	$ git clone https://github.com/hectormiguel1/SafeKepper
	$ cd SafeKepper/src/
	$ javac SafeKepper.java ExecuteShellCommand.java
	$ java SafeKepper 
	 
