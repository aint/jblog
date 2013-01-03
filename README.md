### CODE CONVENTION ###
#### General ####
 * Unix linefeeds (LF) for all files.
 * The [GNU Lesser General Public License version 2.1](http://www.gnu.org/licenses/lgpl-2.1.html) **MUST** be  placed at the top of each and every file.
 * One blank line should always be used at end all files.
 * Make is as simple as possible, but no simpler.

We use the [Code Conventions for the Java Programming Language](http://www.oracle.com/technetwork/java/codeconventions-150003.pdf "Java Code Convention")
and [Code Conventions for the JavaServer Pages Technology](http://www.oracle.com/technetwork/articles/javase/code-convention-138726.html "JSP Code Convention")
with some exceptions.
 
#### Java ####

##### General #####
 * The exact construction of the indentation is 4 spaces. **NO** tabs. It was not easy desigion. We understand that a lot of you like to use tabs, but the fact of the matter is that tab characters cause different interpretation in the spacing of characters in different editors.
 * Java source files shouldn't be large, you should try to make them not more than 300 lines.
 * No more lines in a method then can be viewed in a single screen without scrolling.
 * Javadoc **MUST** exist on all your methods. Also, if you are working on existing code and there currently isn't a javadoc for that method/class/variable or whatever, then you should contribute and add it. This will improve the project as a whole.
 * The max line length is 120 symbols.
 
##### Packages, Classes, Methods and Comments #####
 * Package names should start of the **aint** project domain name (*aint.github.com*), then it should include the name of concrete project (*jblog*), and after that the module name it resides in. *E.g. "com.github.aint.jblog.service".*
 * Try to avoid circle dependencies between packages.
 * Packages shouldn't contain a lot of classes. 100 is too much, but 20-30 is OK.
 * Class names should be nouns, in mixed case with the first letter of each internal word capitalized (*camel style*). *E.g. UserDao, not UserDAO.*
 * Interface and abstract class names shouldn't start with  I and A, respectively. *E.g. MailService, not IMailService; TextEntity, not ATextEntity.*
 * If you can't imagine any suitable name for implementation of some interface, name it *InterfaceNameImpl.*
 * If you don't want/can implement some method and leave it an empty, you must throw *UnsupportedOperationException* in its body.
 * JavaDoc should be present for packages, classes, methods, constants.
 * All JavaDoc sentences of general description should end up with a dot. Exceptions: *param, return, throw* - they may not contain dots, but only if they consist of one sentence; if two or more - you have to put dots after each sentence.
 * Code coverage tests not less than 70%
 
#### Resource Bundle ####
 * Use underscore to separate words in key names, not camel case. *E.g. welcome_message = Hi.*
 * Use dots to separate places where the key is used. E.g.  *header.label.welcome_message = Hi.*

#### XML ####
 * Use 2 spaces for indentation.
 * Tags like this *&lt;tag attribute="value" /&gt;* should contain a space between last attribute and slash.
 
#### JSP ####
 * Use 4 spaces for indentation.
 * Avoid JSP *scriptlets* and *declarations*.
 * Tags like this *&lt;tag attribute="value" /&gt;* should contain a space between last attribute and slash.
 * JSP files or fragment files should contain a server side style comment with *Author(s)* and *Description* elements.
