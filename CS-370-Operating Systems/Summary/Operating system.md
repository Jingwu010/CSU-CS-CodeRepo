## 9. Virtual Memory

### 1. Background

> Most processes do not need all their pages when they swap in
>
> > separation of user logical memory from physical memory

##### 1. Need for Virtual Memory

+ larger address space
+ more processes run at the same time (each use fewer than its maximum)
+ Less I/O needed to load and swap (processes run faster)
+ processes share same address space

##### 2. Virtual address space

+ logical view of how process views memory
+ MMU must map logical address to physical (then find in disk)

### 2. Demand Paging

> Pages of a process are swapped in when needed. (demand)
>
> > All pages of process need not be in physical memory
> >
> > Size of logical address space is no longer constrained by physical memory

##### 1. Basic Concept

+ lazy swapper — never swaps a page into memory unless page will be needed
  + not in memory — seek in disk (latency) + transfer into memory
  + in memory — access
+ **Page Fault** happens when demanded page is not in memory
+ page fault needs context switch becasue disk access is needed (commonly double the time: swap out & swap in)
+ pure demand paging — NO page swaps in until they are requested
+ hardware support needed for demand paging

##### 2. Performance of Demanding paging

+ EAT (effective access time) = (1 - p ) * memory access time

  ​	p * (page fault overhead + swap page out + swap page in)

  ​	_page fault overhead = latency + disk seek time_

### 3. Page Replacement

> Happens when there is no free frames for pages to swap in

##### 1. Basic Concept

+ victim frame — frame that chosen to be swapped out
+ dirty bit (modify bit) — indicates if a process is modified when it in memory (that need to swap out into disk if modified)
+ frame-allocation algorithm
+ page-allocation algorithm

##### 2. FIFO Page Replacement

+ basically more page faults
+ Belady's anomaly — more frames may lead to more page faults

##### 3. Opitimal Page Replacement

+ can not predict the future
+ used for measuring how well one algorithm performs
+ basically the lowest page faults

##### 4. LRU Page Replacement

+ fewer pages faults than FIFO but more than Opitimal
+ generally good and most frequently used

##### 5. LRU Algorithm: Implementation

+ counter implementation (swap out the smallest one)
+ stack implementation (special hardware support & slow)

##### 6. LRU Approximation Algorithm

+ reference bit — indicates the recently usage info.
+ 11000100 is more recently used than 01111111
+ second-chance algorithm — generally FIFO
  + reference bit = 0 —> replace it
  + reference bit = 1 —> set it to 0 and search next 0
+ enhanced second-chance algorithm
  + (0, 0) no recently used, no modified — good to replace
  + (0, 1) no recently used, modified — need swap out
  + (1, 0) recently uesd, no modified — may be used later
  + (1, 1) recently used, modified — high likely used later

##### 7. Counting-Based Page Replacement

+ least frequently used (LFU)
+ most frequently used (MFU)

##### 8. Page Buffering Algorithm

+ keep a buffer (pool) of free frames always
+ keep list of modified pages — write them into disk when idle
+ keep free frame previous contents intact in buffer
  + if reference again —> no need to load from disk

### 4. Allcation of Frames

##### 1. **Minimum number** of frames (depend on specific need of process)

##### 2. Allocation Algorithm

+ Fixed Allocation
  + **Equal Allocation** : each receive the same frames (n processes, m frames, typically (m-c) / n per process )
  + **Proportional Allocation** : allocate according to the size of process (percentage) 
+ **Priority Allocation**: not by size

##### 3. Gobal & Local Allocation

##### 4. Page Alloction:

- Gobal : any page can be victim (all processes)
- Local : replace the page amongst the pages allocated to the process

### 5. Trashing

> a process is busy swaping pages in and out

##### 1. Cause of Trashing

- Cause : A process do not have enough frames, busy swapping in and out pages


- Prevent : To prevent thrashing we must provide processes with as many frames as they really need "right now"
- **Locality model** : processes access memory in given locality one time period

##### 2. Working-Set Model

+ length: delta
+ peaks appears when locality changes

##### 3. Page-Fault Frequency

+ a direct approach to recognize what we eally want to control the page fault rate
+ rate high —> increase page frames
+ rate low —> decrease page frames to an acceptable rate (may be a page-replacement algorithm in the future)

### 6. Memory-Mapped Files

> Memory-mapped file I/O allows file I/O to be treated as routine memory access by mapping a disk block to a page in memory

+ file is then in memory instead of disk
+ rewrite (swap out) to disk at last

### 7. Allocating Kernel Memory

+ treat differently from user memory
+ some need contiguous frames & some need locked page when access

### 8. Other Consideration

##### 1. Prepaging

+ free frame —> enough?
+ yes —> page all into frames
+ no —> predict what need to page (work set)

##### 2. Page Size

- Small pages waste less memory due to internal fragmentation
- Large pages require smaller page tables
- On average, growing over time

##### 3. TLB Reach

- The amount of memory accessible from the TLB (TLB Reach = (TLB Size) X (Page Size))
- Increase the Page Size & Provide Multiple Page Sizes

>Pagesize
>
>+ increase —> smaller pages —> fewer page faults & more fragmentation loss & TLB reach increase
>+ decrease —> more pages —> more page faults & less fragmentation loss & TLB reach decrease & more space needed

##### 4. Program Structure

+ different languages store arrays differently
+ row * column OR column * rows (if row is stored in one page)

##### 5. I/O interlock

— lock the page in memory if

+ kernel operations

+ being selected as a victim to swap out

+ priority low

  ​

  **_Finished in 11/28_**

---

---



## 10. Mass Storage Structure

### 1. Overview of Mass-Storage Structure

##### 1. Magnetic Disks (including Hard Disk)

> Magnetic disks provide bulk of secondary storage of modern computers

+ sectors —> (blocks) —> tracks —> cylinders
+ read-write head
+ seek time — head finds the cylinder
+ latency — head finds the sector
+ Average access time = average seek time + average latency
+ Transfer time = amount to transfer (Gb) / transfer rate (Gb/sec)
+ controller overhead — other time need to finish one operation
+ Average I/O time = average access time + transfer time + controller overhead
+ Drive attached to computer via I/O bus (host controller in computer & disk controller in disk drive)

##### 2. Solid-State Disks (SSD)

> Nonvolatile memory & economics change (no moving head)

+ More expensive & shorter life span
+ lower power consumption & faster & **No moving parts, so no seek time or rotational latency**

##### 3. Magetic Tape

> **Magnetic tape** is a medium for magnetic manager, made of a thin, magnetizable coating on a long, narrow strip of plastic film .

+ Relatively permanent and holds large quantities of data
+ Access time slow
+ Backup & infrequently storage & transfer

### 2. Disk structure

+ logical blocks — 1-dimensional arrays
+ 1-dimensional arrays mapped into _sectors_ of the disk sequentially

### 3. Disk Formatting

+ Low-level formatting — info for disk controller to read and write data


+ high-level formatting — file system format within a disk partition or a logical volume

### 4 .Disk Attachment

##### 1. I/O busses

+ **SCSI** — ports & interface
+ **FC** (fibre channel) — high-speed
+ Storage Array — has controller, provides features to attached host

##### 2. Storage Area Network

+ storage arrays <—> SAN <—> server <—> network <—> client


+ Storage in network shared by clients
+ SAN is one or more storage arrays
+ Easy to add or remove storage, add new host and allocate it storage

##### 3. Network-Attached Storage

+ NAS <—> network <—> client


+ Local storage (Disk) shared through network

### 5. Disk Scheduling

> a fast access time and disk bandwidth

- Seek time ≈ seek distance (between cylinders)
- Average access time = average seek time +average latency
- Average I/O time = average access time + transfertime + controller overhead
- Disk bandwidth = total number of bytes transferred / total time (GB/s)
- OS maintains queue of requests, per disk or device


##### 1. FCFS

##### 2. SSTF(Shortest Seek Time First) 

+ selects the request with the **minimum seek time** from the current head position
+ SSTF scheduling is a form of SJF scheduling; may cause starvation in continous request streams

##### 3. SCAN (elevator algorithm)

+ moves towards one end (to minimun or maximum) and then to another end
+ leads to long wait on the other end

##### 4. C-SCAN (circular scan)

+ moves towards one end and jumps to another end and then moves towards start (serve another end immediately reached one end)

##### 5. LOOK & C-LOOK

+ moves as far as the last request in one direction then reverses direction moves to the start (**no** need to reach the end)

##### 6. Selection of a Disk-Scheduling Algorithm

+ **SSTF** is common and has a natural appeal
+ **SCAN** and **C-SCAN** perform better for systems that place a heavy load on the disk (**Less starvation**)
+ Either **SSTF** or **LOOK** is a reasonable choice for the default algorithm

### 6. Disk Management

##### 1.  Disk Formatting

+ Low-level formatting, or physical formatting — for disk controller to read and writes
+ Logical formatting — for OS in partition or volume

##### 2. Boot Block

> initialize system — the tiny bootstrap code is stored in ROM

+ **Bootstrap loader** program stored in boot blocks of **boot partition** which loads OS.
+ boot loader code (ROM) + partition table (ROM) —> full boot code (Disk) —> OS

### 6. RAID Structure

+ **RAID** originally stood for **Redundant Array of Inexpensive Disks** 


+ multiple disk drives provides **reliability** via redundancy and **performance** via parallelism
+ Mean time to failure — the time one disk fail


+ Mean time to repair — the time one disk recover
+ Mean time to data loss — the time all disks fail (no data recover)
+ **Striping** uses multiple disks in parallel by splitting data (RAID 0)
  + no fault tolerance
+ **Mirroring** keeps duplicate of data on each disk (RAID 1)
  + one disk fail, use the mirrored one
+ **Block parity** One disk hold parity block for other disks. (RAID 5)
  + one disk fail, use the backup parity in other disk
+ **0+1** & **1+0**
  + 0 + 1 — first strip then mirror (series then parallel) : less fault tolerance
  + 1 + 0 — first mirror then strip (parallel then series) : more fault tolerance



​	**_Finished in 11/29_**

---

---



## 11. File System Interface

### 1. File Concept

##### 1.File Attributes

+ Name & Identifier & Type & Location & Size & Protection & Time, data, user identification

##### 2. Disk Structure

+ Volumes —> disks —> partitions
+ Partition can be formatted with a filesystem
+ Entity containing file system known as a volume

### 2. Access Methods

### 3. Dierctory Organization

+ Efficiency — locating a file quickly
+ Naming — convenient to users
+ Grouping — logical grouping of files by properties

##### 1. Single level directory 

+ file name —> file data
+ each file name should be unique

##### 2. Two-level directory 

+ user —> file name —> file data
+ each file name in the same user should be unique

##### 3. Tree-structured directory

##### 4. Acyclic graph (diectory organization)

### 4. File System Mounting

- A file system must be mounted before it can be accessed
- A unmounted file system is mounted at a mount point (directory)
- Merges the file system

### 5. File sharing

##### 1. Multi-user system 

+ User IDs & Group IDs

##### 2. Remote File System

+ distributed file systems & WWW
+ Client-server model
+ Distributed Information Systems

### 6. Protection

##### 1. Access Lists and Groups

---

---

## 12. File-System Implementation

> Logical File System Layer
>
> File Organization Layer
>
> Basic File System Layer

### 1. OS File Data Structure

##### 1. File system <—> secondary storage(Disks / SSDs)

+ rewritable in place
+ direct access (with seek time and latency)

##### 2. File System Layers

+ Device drivers — I/O device at I/O control layers
+ Basic file system — retrieve linear array blocks to device driver
+ File organization module — logical address to physical address
+ logical file system — manages metadata information (file control blocks)

### 2. File-System Implementation

> Based on several on-disk and in-memory structures

##### 1. On disk

+ Boot control block (info needed by system to boot OS)
+ Volume control block (master file table in unix)
+ Directory structure (file names and pointers to FCBs)
+ File control block (many details about the file)

> boot block | super block (VCB) | Directory, FCB | File data blocks 

Create a file

+ allocate new FCB — reads directory into memory — updates filename and FCB — writes back to disk

##### 2. In memory

+ Mount table
+ The open-file tables

##### 3. Partitions and Mounting

+ Partition (raw) — a sequence of blocks with no file system
+ Boot block — point to boot volume or boot loader
+ At mount time, file system consistency checked

##### 4. Virtual File System

+ Object-oriented way of implementing file systems

### 3. Directory Implementation

##### 1. Linear list

+ file names with pointer to the data blocks
+ simple & time consuming

##### 2. Hash table

+ linear list with hash data structure
+ low directory search time
+ collisions — two files names hash to the same location


### 4. Allocation Methods

##### 1. Contiguous allocation

+ each file occupies set of contiguous blocks
+ suffer from external fragmentation
+ _Extent-based_ file systems allocate disk blocks in extents

##### 2. Linked allocation

+ each file a linked list of blocks
+ no external fragmentation
+ low reliability (every block is linked)
+ FAT(File Allocation Table) variation

##### 3. Indexed allocation

+ each file has its own index blocks of pointers to its data block
+ Index table waste space
+ no external fragmentation
+ overhead of index block
+ _Two-level index_

> Most files are small, few large files occupy a large fraction of the space

##### 4. Inode idea

+ hold file metadata including pointers to actual data
+ direct blocks in Volume block — direct pointers to data
+ indirect blocks in Volume block — 1' or  2' or 3' pointers to data

### 5. Allocation Performance

##### 1. Contiguous great for sequential and random data

##### 2. Linked good for sequential, not random

##### 3. Indexed (more complex)

### 6. Free-Space Management

> File system maintains free-space list to track available blocks/clusters

##### 1. Approaches:

+ Bit vector 
  + (00111110) 0 means occupied & 1 means free in specific block
  + Bit map requires extra space
    + blocks for bit map = disk size / block size
+ Linked list
  + travel the free space by pointers
  + cannot get contiguous space easily
  + no waste of space
+ Grouping
  + modify linked list to store N addresses
+ Counting
  + keep track of the first address of contiguous extent or cluster




----

----

## 13. Virtualization

### 1. Implementation of VMMs

+ Type 1 hypervisors

  Operating-system-like software built to provide virtualization

+ Type 2 hypervisors

  Applications that run on standard operating systems but provide VMM feartures to guest operating system (parallel, VMware)

+ Guest Operating System — the OS running on top of the hyperviosr

+ Host Operating System — the OS runs on the hardware executions

### 2. Virtualization benefits

+ Multiple OSes
+ Security
+ Freeze, suspend, running VM
+ Hence — cloud computing

### 3. Builing Block — Trao and Emulate

+ VM needs two modes: Virtual user mode and Virtual kernel mode
+ Trap-and-emulate — Guest OS attempts to execute a privileged instructions (cause a trap —> VMM gains control —> executes)
+ Sensitive instructions
  + some CPUs didn't have clean separation
  + Binary translation solves

### 4. Two types Hyperviors

##### 1. Type 1 Hypervisors

+ Guest OSs believe they are running on bare metal, are unaware of hypervisor.

##### 2. Type 2 Hypervisors

+ Run on top of host OS
+ VMM is simply a process, managed by host OS
+ poorer overall performance (can't take advantage of some HW features)

### 5. Full vs Para-virtualization

+ Full virtualization: Guest OS is unaware of the hypervisor. It thinks it is running on bare metal.
+ Para-virtualization: Guest OS is modified and optimized. It sees underlying hypervisor.

### 6. CPU Scheduling

+ One or more virtual CPUs per guests

  + enough CPUs for all guests — like native OS

  + not enough CPUs (CPU overcommitment) 

    — VMM uses scheduling algorithms to allocate vCPUs

+ Oversubscription of CPUs means guests may get CPU cycles they expect