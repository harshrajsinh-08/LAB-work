#!/bin/bash
echo "Welcome to the Arithmetic Calculator"
echo "Enter the first number:"
read num1
echo "Enter the second number:"
read num2
echo "Select an operation:"
echo "1. Add"
echo "2. Subtract"
echo "3. Multiply"
echo "4. Divide"
read op
case $op in
1)
result=$(echo "$num1 + $num2" | bc)
echo "The sum of $num1 and $num2 is $result"
;;
2)
result=$(echo "$num1 - $num2" | bc)
echo "The difference between $num1 and $num2 is $result"
;;
3)
result=$(echo "$num1 * $num2" | bc)
echo "The product of $num1 and $num2 is $result"
;;
4)
if [ $num2 -eq 0 ]; then
echo "Error: Cannot divide by zero" exit 1
fi
result=$(echo "scale=2; $num1 / $num2" | bc)
echo "The quotient of $num1 and $num2 is $result"
;; *)
echo "Invalid operation" exit 1
;;
esac