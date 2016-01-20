#MVRT's 2016 Code
For documentation in general, visit [our confluence](http://docs.mvrt.com)

##Getting Started
Reference the following link to understand how to get started developing mvrt code: [Cloning the 2016 Repository](http://docs.mvrt.com/display/xix/Cloning+the+2016+Robot+Code)

##Formatting
All indents and tabs must be equivalent to 2 spaces (not 4).
You will not pass the formatting check if they are not set, and your code will be rejected if you choose for some reason to make a pull request.

##Pull Requests
Before you create a pull request, you need to perform some commands:

######Unix:
```bash
a/b/c/frc2016 ~ ./gradlew check
a/b/c/frc2016 ~ ./gradlew assemble
```
######Windows:
```
a\b\c\frc2016 ~ gradlew check
a\b\c\frc2016 ~ gradlew assemble
```
Both of these checks are to make sure your code a) compiles and b) is formatted correctly.
If you don't perform these commands, you will waste valuable time by making pull requests for bad code.
If you are having trouble, contact a code lead and he/she will help you.

####Authorship.
If you do not properly author your commits, they will simply be merged as is and someone else will get credit for your 1000 line PR. I do *not* care what the reason is.
