# RTL-SDR to Cfile Converter
Converting the raw RTL-SDR ouput file to Cfile for GNU Radio and GQRX. 

The script is written in Java and can take 8 bit unsigned files created with RTL-SDR to convert to Cfile for GNU Radio and GQRX.

## Compile
Java Development Kit (JDK) is required. 

```bash
javac rtlsdr2cfile.java
```

## Usage
```bash
java rtlsdr2cfile <input file> <output file>
```
