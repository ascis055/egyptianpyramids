package com.github.ascis055.egyptianpyramids;

// pyramid class, that corresponds to the information in the json file
public class Pyramid {

  protected Integer id;
  protected String name;
  protected String[] contributors;
  protected Pharaoh[] contributors_ref;

  // constructor
  public Pyramid(
    Integer pyramidId,
    String pyramidName,
    String[] pyramidContributors
  ) {
    id = pyramidId;
    name = pyramidName;
    contributors = pyramidContributors;
    contributors_ref = new Pharaoh[contributors.length];
  }

  // print pyramid info
  public void print() {
    int cost = 0;
    System.out.printf("Pyramid %s\n", name);
    System.out.printf("\tid: %d\n", id);
    System.out.printf("\tcontributions:\n");
    for (int n = 0; n < contributors_ref.length; n++) {
      if (contributors_ref[n] != null) {
        cost += contributors_ref[n].contribution;
        System.out.printf("\t\tPharaoh %s: %d gold coins\n",
                          contributors_ref[n].name,
                          contributors_ref[n].contribution);
      }
    }
    System.out.printf("\tCost: %d gold coins\n", cost);
  }
}
