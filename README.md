Topological Sorting work for the Data Strucute class on UNIRIO

Topological Ordering is a process of ordering elements in which a partial order is defined, that is, in which an ordering is carried out only on some pairs of elements and not on their entire set. As an example of applications that use Topological Ordering, we can mention:
A task (e.g. a project) is divided into subtasks. In general, the completion of certain subtasks should precede the execution of other subtasks.

It is denoted by v < w when subtask v must precede subtask w. In this case, topological ordering consists of arranging the subtasks in an order such that, before the start of each task, it is guaranteed that all the subtasks on which it depends have been previously executed;
In a university course curriculum, some subjects must be taken before others, as they are based on the topics presented in the subjects that are their prerequisites. It is denoted by v < w when discipline v is a prerequisite for discipline w. 
Topological ordering, in this case, corresponds to arranging the subjects in such an order that none of them requires as a prerequisite another that has not been previously studied.
In a program, some methods (functions) may have calls to other methods. It is also denoted by v < w when method v is called by method w. 

Topological ordering, in this case, implies arranging procedure declarations in such a way that there are no references to previously undeclared methods.
In general, a partial ordering of a set S corresponds to a binary relationship between the elements of S. This relationship, denoted by the symbol <, verbally
read as above, it must have the following properties for any elements distinct from S:
1. if x < y and y < z, then x < z
2. if x < y, then y < x does not occur 3. z < z does not occur
(transitivity); (asymmetry); and (irreflexive).

The program generates then reads a file that contais a list of binary relationships in a DAG format, then organizes its data to show each element and the respective predecessors and sucessors.
At last, the topological sorting is made and the result printed on screen.
