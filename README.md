# n0mu
![example gif](example.gif)

n0mu is a proof-of-concept cloth simulator built using Java and Salty Engine.

It is not optimized at all and uses a very naive shading algorithm.

## The algorithms
Each cloth ("ropes" and "sheets") consist of `ClothPoint`s, `ClothConstraint`s and `ClothFace`s.

Each `ClothConstraint` has two endpoints (each being `ClothPoints`); it "tries" to keep them the same distance apart.

There is also gravity going on and Newton is being respected.

##&nbsp;&nbsp;&nbsp;&nbsp; Shading
Each face is colored with a blend of its color and black. The ratio of the blend is determined by the ratio of the current area of the face to the initial area.
This works fine in most cases and makes for a pretty convincing 3D effect.