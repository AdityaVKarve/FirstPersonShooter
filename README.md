# FirstPersonShooter
A first person shooter being made exclusively in Java. 

<h3>Overview</h3>
My first attempt at making a game in a 3D first person perspective. The game takes after the old wolfenstein games, being capable of rendering flat walls only (unlike the angular level geometry Doom is capable of). Levels are hardcoded in a class, created by defining wall positions, and the textures applied to said walls. The technique I use to render 3D is using scanline rendering, along with a zbuffer to create depth.

![gloom_rendering](https://user-images.githubusercontent.com/29706261/166151631-f399d0f7-b502-410d-8057-9acf0b944f63.gif)
<h6>Darkness created to hide what's in the distance via a zbuffer. The recording is from an older version, where there were no enemies implemented, or clip sizes.</h6>

<h3>Description:</h3>
Break out of the POW camp by taking down a prison guard and siezing his weapon! 
Guards are passive until they are attacked, or until you break out of your enclosure.

<h3>Weapons:</h3>
Fists: Your basic melee attack. They do low damage and work at point blank only.
Thompson SMG: A rapid firing SMG. Can do serious damage in a sustained burst, and has a large magazine capacity of 50 rounds.
Mauser: A bolt action rifle. Can take out enemies at great distance with a single hit. Compensated by a low clip size and slow rate of fire.

<h3>Pickups:</h3>
Med-kit: A pickup that raises your health by 10 points, to a max of 110. 
Thompson SMG: Adds the SMG to your inventory if it wasn't present before and adds 30 SMG rounds. Dropped by guards on death.
Mauser: Adds the Mauser rifle to your inventiory if it wasn't present before, and adds 5 rifle rounds. Currently only obtainable by editing the code.

<h3>Music:</h3>
The music is a royalty free copy of the Doom E1M1 soundtrack.
Weapon and Guard sounds are royalty free copies of sounds used in Return to castle: Wolfenstein.


<h3>More gameplay screengrabs:</h3>

![gpalysmall](https://user-images.githubusercontent.com/29706261/166153272-721a2135-f5a4-4301-a2ae-0797c7d41549.gif)
<h6>Taking out a guard with fists and picking up his weapon, using that on another guard. Gif size has been limited to fit uploading restrictions.</h6>
