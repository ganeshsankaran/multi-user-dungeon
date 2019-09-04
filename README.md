# Multi-User Dungeon
<p>by Ganesh Sankaran</p>
<p>Developed using Java 8</p>
<hr />
<h3>Deploying the Client and Server</h3>
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
