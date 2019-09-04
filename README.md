# Multi-User Dungeon
<p>by Ganesh Sankaran</p>
<p>Developed using Java 8</p>
<hr />
<h3>Overview</h3>
<p>MUD is split into server-side and client-side components, namely MUDServer.java
and MUDClient.java. MUDServer actively listens for users connecting from
MUDClient and creates new threads sharing memory containing information about
the other users as well as the distribution of rooms and users within the virtual
world.</p>
<h3>Dependencies</h3>
<ul>
  <li>JDK 8</li>
  <li>A network connection</li>
</ul>
<h3>Deploying the Client and Server</h3>
<p>Included are the files MUDServer.java, MUDClient.java, Coordinates.java,
Dungeon.java, and Room.java. Please note that MUDServer.java depends on
Coordinates.java, Dungeon.java, and Room.java, whereas MUDClient.java is designed
to run independently.</p>
<strong>1. Obtaining the Source Code</strong>
<p>a. Clone the Repository</p>
<pre>git clone https://github.com/ganeshsankaran/multi-user-dungeon.git</pre>
<br />
<strong>2. Deploying the Server</strong>
<p>a. Compile the server source code</p>
<pre>javac MUDServer.java</pre>
<p>b. Run the server</p>
<pre>java MUDServer [world dimension] [maximum number of users]</pre>
<i>Example: <code>java MUDServer 100 1000</code></i>
<br />
<br />
<strong>3. Deploying the Client</strong>
<p>a. Compile the client source code</p>
<pre>javac MUDClient.java</pre>
<p>b. Run the client</p>
<pre>java MUDClient [server IP]</pre>
<i>Example: <code>java MUDClient 127.0.0.1</code></i>
<br />
<br />
<h3>Using the Client</h3>
<p>When the MUDClient JFrame opens up, please enter a non-empty name that does
not contain parentheses or forward slashes. If your name has already been taken,
the client will prompt you for a different name immediately.</p>
<p>Most of the window’s area outputs information in response to the commands you
enter. For example, room descriptions, the names of users in your room, the
movement of users into and out of your room, and dialogs are displayed here.</p>
<p>In this implementation, the rooms are empty (containing no items). In addition, the
description of the rooms is just a pseudorandom number as a proof-of-concept.
Implementing items and the commands associated with them is simple, which can
be implemented using another data structure (such as a hash-table mapping the
item name to the number of that item available in the room) to store each room’s
items in the Room class.</p>
<p>Commands can be entered in the text field at the bottom of the window. The
following commands have been implemented thus far:</p>
<ul>
  <li><code>/say/[dialog]</code></li>
  <li><code>/tell/[username]/[dialog]</code></li>
  <li><code>/yell/[dialog]</code></li>
  <li><code>/north</code></li>
  <li><code>/south</code></li>
  <li><code>/east</code></li>
  <li><code>/west</code></li>
  <li><code>/up</code></li>
  <li><code>/down</code></li>
  <li><code>/quit</code></li>
</ul>
 
