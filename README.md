# vacation
Some solutions to the problem of validating vacation requests

## Files

- `Vacation.java` - Java code to read a text file of vacation requests and validate them
- `test-data-*.txt` - Sample test data files

## Notes

The code was compiled and tested on macOS HighSierra 10.13.6.

## Sample output
```
$ java Vacation < test-data-01.txt
n=5 m=2
(1,4)
(1,4)
(2,5)
(3,4)
Performing checkVacationWorse()
Day range: 1 to 5
On day 1, have 3 employees in the office
On day 2, have 2 employees in the office
On day 3, have 1 employees in the office
Vacation not okay
Performing checkVacationBetter()
On day 1, have 3 employees in the office
On day 2, have 2 employees in the office
On day 3, have 1 employees in the office
Vacation not okay
```
