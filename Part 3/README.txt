This is an implementation of the dining philosopher problem. In order to prevent deadlock I use a monitor with synchronized pickup and putdown methods.
In order to prevent starvation I use an ArrayList called 'lineUp' which acts as a queue, alongside an array of integers called 'turnsWaited' which acts as what I call a "patience monitor".

Whenever a philosopher wishes to eat but can't, they get added to the queue. As well as this, the philosopher has their position in turnsWaited incremented by 1 to signify that they had to wait to eat.

Whenever a philosopher goes to eat, they check if someone is ahead of them in the queue, and if there is they then check that philosopher's patience in 'turnsWaited'. If they haven't waited too long, the philosopher will choose to eat.
If a philosopher has "run out of patience" they will be the only one allowed to eat, after which their patience will be reset.

I have included a public final int at the top of the Monitor class called 'POLITENESS'. By default this is set to 5 which means a philosopher can have someone "cut in front of them" 5 times before they run out of patience.

Feel free to reduce or increase this number to change the behavior of the philosophers. Set POLITENESS too high, and philosophers will start waiting extreme amounts of time to eat. Set it too low, and the philosophers will go to extreme
lengths to respect the queue. Usually culminating in only one philosopher being allowed to eat at any given time.

In order to decide how many philosophers will be seated at the table, you will be prompted to input an integer at the start of the program. If you enter '-1' you will get whatever 'DEFAULT_NUMBER_OF_PHILOSOPHERS' is set to.

Otherwise, any positive integer will do.

Output of 3 separate tests can be found in the other text files located in this assignment, labelled '#PhilOutput.txt'.

As for the philosopher's desire to communicate, I use the synchronized methods requestTalk() and endTalk() alongside a boolean called 'isSomeoneTalking' to ensure no two philosophers are talking at the same time.
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Extra notes on the pickUp() method: 
When a philosopher's thread ID is passed into the pickUp() method, I create an int called 'chopstick_index' which will keep track of which chopsticks in the chopstick array are next to this particular philosopher.

I will use this index to check the status of the chopstick on their left and right, as well as use it to keep track of the philosopher's index in 'turnsWaited'.

While an ArrayList is not necessarily a queue, I make sure that philosophers are only ever added to the back of it, and removed from the front.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Thank you for your time!