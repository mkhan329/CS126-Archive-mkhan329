// Copyright (c) 2020 CS126SP20. All rights reserved.

#include "ll.h"

#include <cassert>
#include <utility>
#include <vector>

// TODO(you): Implement these methods.

namespace cs126linkedlist {

template <typename ElementType>
LinkedList<ElementType>::LinkedList() {
  head_ = nullptr;
  tail_ = nullptr;
}

template <typename ElementType>
LinkedList<ElementType>::LinkedList(const std::vector<ElementType>& values) {
  head_ = nullptr;
  tail_ = nullptr;
  for(ElementType a : values) {
    this->push_back(a);
  }
}

// Copy constructor
template <typename ElementType>
LinkedList<ElementType>::LinkedList(const LinkedList<ElementType>& source) {
  head_ = nullptr;
  tail_ = nullptr;
  for(auto i = source.begin(); i!= source.end(); ++i){
    this->push_back(*i);
  }
}

// Move constructor
template <typename ElementType>
LinkedList<ElementType>::LinkedList(LinkedList<ElementType>&& source) noexcept {
  head_->value = source.head_->value;
  head_->next_ = source.head_->next_;
  tail_->value = source.tail_->value;
  tail_->next_ = nullptr;
  delete source;
}

// Destructor
template <typename ElementType>
LinkedList<ElementType>::~LinkedList() {
  this->clear();
  delete this;
}

// Copy assignment operator
template <typename ElementType>
LinkedList<ElementType>& LinkedList<ElementType>::operator=(
    const LinkedList<ElementType>& source) {
  head_ = source.head_;
  tail_ = source.tail_;
}

// Move assignment operator
template <typename ElementType>
LinkedList<ElementType>& LinkedList<ElementType>::operator=(
    LinkedList<ElementType>&& source) noexcept {
  head_ = source.head_;
  tail_ = source.tail_;
  delete source;
}

template <typename ElementType>
void LinkedList<ElementType>::push_front(const ElementType& value) {
  Node* tmp;
  tmp->value = value;
  tmp->next_ = head_;
  head_ = tmp;
}

template <typename ElementType>
void LinkedList<ElementType>::push_back(const ElementType& value) {
  if(head_ == nullptr) {
    head_->value = value;
    head_->next_ = nullptr;
  } else {
    Node* tmp;
    tmp->value = value;
    tmp->next_ = nullptr;
    tail_->next_ = tmp;
    tail_ = tmp;
  }
}

template <typename ElementType>
ElementType LinkedList<ElementType>::front() const {
  return head_->value;
}

template <typename ElementType>
ElementType LinkedList<ElementType>::back() const {
  return tail_->value;
}

template <typename ElementType>
void LinkedList<ElementType>::pop_front() {
  Node* tmp;
  tmp = head_->next_;
  delete head_;
  head_ = tmp;
}

template <typename ElementType>
void LinkedList<ElementType>::pop_back() {
  Node* tmp = head_;
  while(tmp->next_!= tail_) {
    tmp = tmp->next_;
  }
  delete tail_;
  tail_ = tmp;
}

template <typename ElementType>
size_t LinkedList<ElementType>::size() const {
  if(head_ == nullptr) {
    return 0;
  }
  size_t j = 1;
  for(auto i = this->begin(); i!= this->end(); ++i){
    j++;
  }
  return j;
}

template <typename ElementType>
bool LinkedList<ElementType>::empty() const {
  return head_ == nullptr;
}

template <typename ElementType>
void LinkedList<ElementType>::clear() {
  Node* tmp = head_;
  while(tmp != nullptr) {
    delete tmp;
    tmp = tmp->next_;
  }
  head_ = nullptr;
}

template <typename ElementType>
std::ostream& operator<<(std::ostream& os,
                         const LinkedList<ElementType>& list) {
  if(list.empty()) {
    std::cout << "null" << std::endl;
    return os;
  }
  for (auto i = list.begin(); i!= list.end(); ++i) {
    os << (*i) << ",";
  }
  return os;
}

template <typename ElementType>
void LinkedList<ElementType>::RemoveNth(size_t n) {
  if(n >= this->size() || n < 0){
    return;
  }
  if (n == this->size() - 1) {
    this->pop_back();
  }
  if (n == 0) {
    this->pop_front();
  }
  Node* j = this->head_;
  for(size_t i = 0; i < n - 1; i++) {
    j = j->next_;
  }
  j->next_ = j->next_->next_;
  delete[] j->next_;

}

template <typename ElementType>
void LinkedList<ElementType>::RemoveOdd() {
  for(int i = 0; i < this->size(); i++) {
    if (i % 2 != 0) {
      this->RemoveNth(i);
    }
  }
}

template <typename ElementType>
bool LinkedList<ElementType>::operator==(
    const LinkedList<ElementType>& rhs) const {
  if(this->size() != rhs.size()) {
    return false;
  }
  Node j = rhs.head_;
  for(Node i = head_; i->next_ != nullptr; i = i->next_) {
    if (j->value != i->value) {
      return false;
    }
    j = j->next_;
  }
  return true;
}

template <typename ElementType>
bool LinkedList<ElementType>::operator!=(
    const LinkedList<ElementType>& rhs) const {
  if(this->size() != rhs.size()) {
    return true;
  }
  Node j = rhs.head_;
  for(Node i = head_; i->next_ != nullptr; i = i->next_) {
    if (j->value == i->value) {
      return false;
    }
    j = j->next_;
  }
  return true;
}

template <typename ElementType>
typename LinkedList<ElementType>::iterator& LinkedList<ElementType>::iterator::
operator++() {
  current_ = current_->next_;
  return *this;
}

template <typename ElementType>
ElementType& LinkedList<ElementType>::iterator::operator*() const {
  return current_->value;
}

template <typename ElementType>
bool LinkedList<ElementType>::iterator::operator!=(
    const typename LinkedList<ElementType>::iterator& other) const {
  return current_ != other.current_;
}

template <typename ElementType>
typename LinkedList<ElementType>::iterator LinkedList<ElementType>::begin() {
  return iterator(head_);
}

template <typename ElementType>
typename LinkedList<ElementType>::iterator LinkedList<ElementType>::end() {
  return iterator(tail_);
}

template <typename ElementType>
typename LinkedList<ElementType>::const_iterator&
LinkedList<ElementType>::const_iterator::operator++() {
  current_ = current_->next_;
  return *this;
}

template <typename ElementType>
const ElementType& LinkedList<ElementType>::const_iterator::operator*() const {
  return current_->value;
}

template <typename ElementType>
bool LinkedList<ElementType>::const_iterator::operator!=(
    const typename LinkedList<ElementType>::const_iterator& other) const {
  return current_ != other.current_;
}

template <typename ElementType>
typename LinkedList<ElementType>::const_iterator
LinkedList<ElementType>::begin() const {
  return const_iterator(head_);
}

template <typename ElementType>
typename LinkedList<ElementType>::const_iterator LinkedList<ElementType>::end()
    const {
  return const_iterator(tail_);
}

}  // namespace cs126linkedlist
