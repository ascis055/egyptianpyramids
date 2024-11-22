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
}
