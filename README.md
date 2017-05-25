# ipblock

The ipblock class provides both testing for membership within a subnet as well as enumerating all ip addresses within a subnet.

## Compiling and Running:

### Testing for membership: 
~~~~
javac ipblock.java
java ipblock subnet/prefix addr
~~~~

*Example output*
~~~~
java ipblock 12.23.34.45/16 12.22.35.44
not in block
~~~~

### Enumerating:
~~~~
javac ipblock.java
java ipblock subnet/prefix
~~~~

*Example output*
~~~~
java ipblock 12.23.34.45/29
12.23.34.40
12.23.34.41
12.23.34.42
12.23.34.43
12.23.34.44
12.23.34.45
12.23.34.46
12.23.34.47
~~~~

## Testing:

A sample bash script has been provided for several testcases for the membership problem. The testcases are written, one per line, in `testcases` and can be executed with `test.sh` as follows: 

~~~~
./test.sh
~~~~

Care was taken to provide graceful exiting for malformed inputs (out of range values, numberformat exception ...etc), and several of the test cases address those issues. Test cases also include edge cases, such as the prefix being equal to zero or 32, as well as other basic test cases.

Feel free to add more testcases as needed, however the `testcases` file expects an empty line at the end of file.

