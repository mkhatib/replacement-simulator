This is an Operating System Course Project.

Build a paging simulator that “executes” reference strings and swaps pages in and out according to a replacement policy. The pages can be replaced globally. Assume the page size is 1KB. Use the simulator to test two paging policies FIFO and LRU against several reference strings. For each policy, you must test the same reference string on different memory sizes 16KB, 32KB, 64KB, and 256KB.

You will test the following three reference strings:
A list of 100 references to 10 virtual pages, to be used for debugging your paging simulator.
A list of 1000 references to 100 virtual pages, uniformly distributed.
A list of 1000 references to (up to) 1000 virtual pages, distributed according to a Gaussian (normal) Distribution.

For each reference string simulation, output chart is needed. The output chart will be as follow:
Horizontal axis showing physical memory sizes
Vertical axis showing number of page faults, 2 lines, one for each policy.