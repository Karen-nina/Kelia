import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import PostList from "./PostList";

jest.mock("../../helpers/renderContentWithMentions", () => ({
  renderContentWithMentions: jest.fn((content) => `Processed: ${content}`),
}));

jest.mock("../../helpers/timeAgo", () => ({
  timeAgo: jest.fn(() => "some time ago"),
}));

jest.mock("../../components/Inicio/PostCommentsModal", () => (props) => (
  <div data-testid="post-comments-modal">
    <button onClick={props.onClose}>Close Modal</button>
    <div>{JSON.stringify(props.post)}</div>
  </div>
));

jest.mock("./Post", () => (props) => (
  <div data-testid="post">
    <div>{props.user}</div>
    <div>{props.content}</div>
    <div>{props.comments} comments</div>
    <div>{props.likes} likes</div>
    <div>{props.date}</div>
  </div>
));

describe("PostList Component", () => {
  const { renderContentWithMentions } = require("../../helpers/renderContentWithMentions");
  const { timeAgo } = require("../../helpers/timeAgo");

  test("ShowsSpinnerWhenLoadingIsTrue", () => {
    render(<PostList posts={[]} loading={true} highlightTerm="" />);
    expect(screen.getByText(/cargando/i)).toBeInTheDocument();
  });

  test("ShowsNoCommentsMessageWhenPostsIsEmptyAndNotLoading", () => {
    render(<PostList posts={[]} loading={false} highlightTerm="" />);
    expect(screen.getByText(/No hay comentarios disponibles/i)).toBeInTheDocument();
  });

  test("RendersListOfPosts", () => {
    const posts = [
      {
        user: "User1",
        content: "Content1",
        comments: 2,
        likes: 5,
        createdAt: new Date(),
        mentionedRecipes: [],
        mentionedProducts: [],
        mentionedPlaces: [],
      },
      {
        user: "User2",
        content: "Content2",
        comments: 3,
        likes: 1,
        createdAt: new Date(),
        mentionedRecipes: [],
        mentionedProducts: [],
        mentionedPlaces: [],
      },
    ];

    render(<PostList posts={posts} loading={false} highlightTerm="term" />);

    const postElements = screen.getAllByTestId("post");
    expect(postElements).toHaveLength(2);

    expect(renderContentWithMentions).toHaveBeenCalledWith(
      "Content1",
      "term",
      expect.any(Object)
    );
    expect(timeAgo).toHaveBeenCalled();

    expect(postElements[0]).toHaveTextContent("User1");
    expect(postElements[1]).toHaveTextContent("User2");
  });

  test("OpensModalWithProcessedPostOnPostClick", async () => {
    const posts = [
      {
        user: "User1",
        content: "Content1",
        comments: 2,
        likes: 5,
        createdAt: new Date(),
        mentionedRecipes: ["recipe1"],
        mentionedProducts: ["product1"],
        mentionedPlaces: ["place1"],
      },
    ];

    render(<PostList posts={posts} loading={false} highlightTerm="term" />);

    expect(screen.queryByTestId("post-comments-modal")).not.toBeInTheDocument();

    fireEvent.click(screen.getByTestId("post").parentElement);

    await waitFor(() => {
      expect(screen.getByTestId("post-comments-modal")).toBeInTheDocument();
      expect(screen.getByTestId("post-comments-modal")).toHaveTextContent("Processed: Content1");
      expect(screen.getByTestId("post-comments-modal")).toHaveTextContent("User1");
    });
  });

  test("ClosesModalOnCloseButtonClick", async () => {
    const posts = [
      {
        user: "User1",
        content: "Content1",
        comments: 2,
        likes: 5,
        createdAt: new Date(),
        mentionedRecipes: [],
        mentionedProducts: [],
        mentionedPlaces: [],
      },
    ];

    render(<PostList posts={posts} loading={false} highlightTerm="" />);

    fireEvent.click(screen.getByTestId("post").parentElement);

    await waitFor(() => {
      expect(screen.getByTestId("post-comments-modal")).toBeInTheDocument();
    });

    fireEvent.click(screen.getByText("Close Modal"));

    await waitFor(() => {
      expect(screen.queryByTestId("post-comments-modal")).not.toBeInTheDocument();
    });
  });
});