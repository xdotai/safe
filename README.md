## safe

[![Join the chat at https://gitter.im/xdotai/safe](https://badges.gitter.im/xdotai/safe.svg)](https://gitter.im/xdotai/safe?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Helpers to write type-safe Scala code using equality and Strings.

(Usage below.)

### Build setup

#### SBT

```scala
"ai.x" %% "safe" % "1.2.0"
```

#### CBT

```scala
ScalaDependency( "ai.x", "safe", "1.2.0" )
```

### imports
```scala
import ai.x.safe._
```

### Feedback welcome

Are `~` or `===` conflicting with other libraries you use?

For now do

```scala
import ai.x.safe.{ SafeEquals => _, SafeStringAdd => _, _ }
```

But open a ticket if anything is getting in your way or you have ideas how to improve things, etc.

### Contributions welcome

Found any other type-safety holes in the Scala Standard Library and know a workaround? Why not submit a PR :)?

### Usage / Problems this library solves

```scala
import ai.x.safe._
```

#### == is not type-safe

##### Problem
```scala
val guests = List( chris, ... )
chris == guests.find( _ == chris )
// false
```

##### Solution: ===
```scala
val guests = List( chris, ... )
chris === guests.find( _ == chris )
// type error: Guest != Option[Guest]
```

#### .contains is not type-safe
##### Problem
```scala
val guests = List( chris, ... )
guests.contains( chrisId )
// false
```

##### Solution
```scala
val guests = List( chris, ... )
guests.safeContains( chrisId )
// error expected Guest, found GuestId
```

#### + is not type-safe
##### Problem
```scala
"Hi " + guest + ", ..."
// Hi Guest(1,Some(Chris)), ...
```

##### Solution
```scala
"Hi " ~ guestName ~ ", ..."
// error expected Guest, found Option[Guest]
```

#### .mkString is not type-safe
##### 
```scala
"Hi " + names.mkString(" ") + ", ..."
// Hi Some(Chris) Some(Vogt), ...
```

##### Solution
```scala
"Hi " + names.safeMkString(" ") + ", ..."
// safeMkString is not a member of List[Option[String]]
```

#### s"..." is not type-safe
##### Problem
```scala
s"Hi $guestName, ..."
// Hi Some(Chris), ...
```

##### Solution
```scala
safe"Hi $guestName, ..."
// expected String, found Option[String]
```
